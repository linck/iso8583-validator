<div align="center">
  <h1><strong>ISO8583 Validator</strong></h1>
  <a href="https://github.com/linck/iso8583-validato">
    <img src="https://github.com/linck/iso8583-validator/blob/master/icon_256.png">
  </a>
  <br>
<h2><strong>Ferramenta para auxiliar na validação da mensageria ISO8583
</strong></h2>
</div>


--------
## Pre-requisitos

### Mensagens para validação

Na pasta do da aplicação tem dois arquivos xml.

- **logiso.xml** : Colocar o conteudo do log xml transação que quer validar
- **logiso_original.xml** : **Opcional.** Para casos de transações que precisam de transação original para ser validada. Como por exemplo: **Pré-autorização Substitutiva, Confirmação de Pré-autorização, Estornos ...** precisam da transação original pra validar o bit 90, bit 120 e outros

### Criação dos Validators

É nécessário configurar os validators através do arquivo **validators.json**. Ver sessão **Validators** para mais informações sobre como fazer isso.

--------

## Como Usar

### Executar a transção

Execute a transação desejada para o ambiente montado de sua preferência

### Coletar logs

- Colete o os logs xml da transação feita e da transação original, se for necessário. 
- O  conteúdo log do da transação a ser validada coloque no arquivo **logiso.xml** 
- O conteúdo do log da transação original, se for necessário, coloque no arquivo **logiso_original.xml**

### Executar a aplicaçao

```bash
iso8583-validator [nome_do_validador]
```

# Validators

Os validators são as regras que o **Iso8583-validator** irá seguir para fazer a validação da mensagem informada nos arquivos .xml.
Estes deverão ser definidos no arquivo **validators.json** que está na raiz da aplicação.

## Campos

Um validador sempre tem como o atributo inicial o **nome validador**, este será usando como uma chave que o identifica. Deve ser único. Este será usado como argumento da linha de comando da aplicação:

Exemplo:

```json
{
  "nome_do_validador": { 
	"mandatoryFields": [
		3,
		4
	],
	"fields": [
		{
		"id": "22",
		"mandatory": true,
		"regex": "051"
		}
	}
}
```

Comando na hora de chamar na aplicação:
```bash
iso8583-validator nome_do_validador
```

# Exemplo com todos campos possíveis:


```json
{
  "cenario_1_id_1-Crédito": {
    "extendsOf": "cenario_5_id_1-Débito",
    "extendsOfRemoveFields": [55],
    "notExistsFields": [35],
    "mandatoryFields": [ 3, 4, 7, 11, 12, 13],
    "fields": [
      {
        "id": "48",
        "description": "Dados adicionais da transação",
        "mandatory": true,
        "regex": "[A-Z][a-z][0-9]",
        "mandatoryIfExistsField": "11",
        "mandatoryIfNotExistsField": "55",
        "containsOriginalFields":[11,7],
        "contains": "alguma string no bit para validar",
        "subfields": [  (Todos os campos dos "fields" vale para os "subfields")
          {
            "id": "001",
            "description": "Subcampo 1",
            "mandatory": true,
            "regex": "[A-Z][a-z][0-9]",
            "mandatoryIfExistsField": "11",
            "mandatoryIfNotExistsField": "55",
            "containsOriginalFields":[11,7],
            "contains": "alguma string no bit para validar",
          },
          {
              Subcampo 2....
          },
          {
              Subcampo 3....
          }
        ]
      },
      {
          Campo 2....
      },
       {
          Campo 3....
      }
  }

```


# Descrição dos campos

## Validator

| Campo                 | Tipo           | Descrição                                                                                                                                                                                                                       |
|-----------------------|----------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| mandatoryFields       | Array String   | Informa os campos que devem ser tratados como obrigatórios na mensageria para esse validador                                                                                                                                    |
| extendsOf             | String         | Informa o nome de outro validator que você deseja ter como base. Todos os campos desse validador "pai" serão importados para o novo. Os campos definidos no novo validador terão prioridade em relação aos que serão importados |
| extendsOfRemoveFields | Array String   | Informa os campos que não queremos que seja importado do validador que foi informado em "extendsOf"                                                                                                                             |
| notExistsFields       | Array String   | Valida se o campo não existe na mensageria. Útil para validar campos que não deveriam vir.                                                                                                                                      |
| fields                | FieldValidator | Estrutura com regras para validação específica para cada campo.                                                                                                                                                                 |

## FieldValidator

| Campo                     | Tipo           | Obrigatório | Descrição                                                                                                                                                                                                                                                                                                                                                                                 |
|---------------------------|----------------|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| id                        | String         | Sim         | Id do campo. Corresponte ao bit do ISO8583. **Ex: "2", "11", "55", "127"**                                                                                                                                                                                                                                                                                                                |
| description               | String         | Não         | Descrição do campo                                                                                                                                                                                                                                                                                                                                                                        |
| mandatory                 | Boolean        | Sim         | Informa se é obrigatório ou não                                                                                                                                                                                                                                                                                                                                                           |
| regex                     | String         | Não         | Valida o conteúdo da mensagem através de uma Expressão Regular.   Pode ser usar também para validação de um texto literal.  **Por exemplo, para validar se todo bit 11 é igual a "12456": "regex": "123456"**                                                                                                                                                                                 |
| mandatoryIfExistsField    | String         | Não         | Valida o campo como obrigatório se o bit informado existir                                                                                                                                                                                                                                                                                                                                |
| mandatoryIfNotExistsField | String         | Não         | Valida o campo como obrigatório se o bit informado **não** existir                                                                                                                                                                                                                                                                                                                        |
| containsOriginalFields    | String         | Não         | Valida se no conteúdo do bit contém o valor do bit informado da transação original.  Esse campo serve para validar bits que possuem informações da transação original, como por exemplo o bit 90 e bit 120. É preciso informar o conteúdo da transação original no arquivo **logiso_original.xml** que está na raiz da aplicação.                                                         |
| contains                  | String         | Não         | Valida se dentro do conteúdo do bit contém o valor informada.                                                                                                                                                                                                                                                                                                                             |
| subfields                 | FieldValidator | Não         | Valida os subcampos que são em **formato TLV**. Essa estrutura suporta todos campos dos "fields" já descritos acima.   **Obs:**  Os ids dos subcampos TLV possuem 3 posições. Por exemplo o subcampo 1 deverá ser informado da seguinte forma:   **"id": "003"** |
