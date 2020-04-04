# Validators

Os validators são as regras que o **Iso8583-validator** irá seguir para fazer a validação da mensagem informada nos arquivos .xml
Estes deverão ser definidos no arquivo **validators.json** que estpa na raiz da aplicação.

## Campos

Um validador sem tem como o atributo inicial o **nome validador**, este será usando como uma chave que o identifica. Deve ser único. Este será usado como argumento da linha de comando da aplicação:

Exemplo:

```json
                        {
Nome do validador ----> "cenario_5_id_1-preauth_subs_emv_senha_on": { 
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
iso8583-validator cenario_5_id_1-preauth_subs_emv_senha_on
```

# Exemplo com todos campos possíveis:


```json
{
  "cenario_5_id_1-Crédito": {
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
        "subfields": [ --> Todos os campos dos "fields" vale para os "subfields"
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

| Campo                     | Tipo   | Obrigatório | Descrição                                                                                                                                                                                                                                                                                                                                                                                 |
|---------------------------|--------|-------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| id                        | String | Sim         | Id do campo. Corresponte ao bit do ISO8583. **Ex: "2", "11", "55", "127"**                                                                                                                                                                                                                                                                                                                |
| description               | String | Não         | Descrição do campo                                                                                                                                                                                                                                                                                                                                                                        |
| mandatory                 |        | Sim         | Informa se é obrigatório ou não                                                                                                                                                                                                                                                                                                                                                           |
| regex                     |        | Não         | Valida o conteúdo da mensagem através de uma Expressão Regular.   Pode ser usar também para validação de um texto literal.  **Por exemplo, para validar se todo bit 11 é igual a "12456": "regex": "123456"**                                                                                                                                                                                 |
| mandatoryIfExistsField    |        | Não         | Valida o campo como obrigatório se o bit informado existir                                                                                                                                                                                                                                                                                                                                |
| mandatoryIfNotExistsField |        | Não         | Valida o campo como obrigatório se o bit informado **não** existir                                                                                                                                                                                                                                                                                                                        |
| containsOriginalFields    |        | Não         | Valida se no conteúdo do bit contém o valor do bit informado da transação original.  Esse campo serve para validar bits que possuem informações da transação original, como por exemplo o bit 90 e bit 120. É preciso informar o conteúdo da transação original no arquivo **logiso_original.xml** que está na raiz da aplicação.                                                         |
| contains                  |        | Não         | Valida se dentro do conteúdo do bit contém o valor informada.                                                                                                                                                                                                                                                                                                                             |
| subfields                 |        | Não         | Valida os subcampos que são em **formato TLV**. Essa estrutura suporta todos campos dos "fields" já descritos acima.   **Obs:**  Os ids dos subcampos TLV possuem 3 posições. Por exemplo o subcampo 1 deverá ser informado da seguinte forma:   **"id": "003"** |


## Exemplo de Validators:

```json
{
  "cenario_5_id_1-preauth_subs_emv_senha_on": {
    "mandatoryFields": [
      3,
      4,
      7,
      11,
      12,
      13,
      22,
      41,
      42,
      48,
      49,
      61
    ],
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "051"
      },
      {
        "id": "48",
        "mandatory": true,
        "subfields": [
          {
            "id": "003",
            "mandatory": true
          },
          {
            "id": "005",
            "mandatoryIfExistsField": "2"
          },
          {
            "id": "005",
            "mandatoryIfExistsField": "35"
          },
          {
            "id": "012",
            "mandatory": true
          },
          {
            "id": "010",
            "description": "Soft Descriptor - Verifique se esta cadastrado na paystore",
            "mandatory": true
          }
        ]
      },
      {
        "id": "52",
        "mandatory": true,
        "regex": "6FDD228FE1F0B989"
      },
      {
        "id": "55",
        "mandatory": true,
        "regex": "FF20\\d\\d820218009F2701809F2608B46309C2B0FD3E709F360201E3950580400080009F34030100029F3704E8D4C21B9F3303E0F8C05F280200769F100706010C03A080009A031902055F3401014F07A00000000310109F0B08526F646F6C70686F5F2008526F646F6C70686F"
      },
      {
        "id": "61",
        "mandatory": true,
        "subfields": [
          {
            "id": "001",
            "description": "Dados da aplicação instalada na loja",
            "mandatory": true
          },
          {
            "id": "002",
            "description": "Número de série do PINPad",
            "mandatoryIfExistsField": "52"
          },
          {
            "id": "004",
            "description": "Versão da aplicação Gerenciadora",
            "mandatoryIfExistsField": "52"
          },
          {
            "id": "003",
            "description": "Dados para formatação do campo 61",
            "mandatory": true
          },
          {
            "id": "005",
            "description": "Fabricante do Pinpad",
            "mandatoryIfExistsField": "55"
          },
          {
            "id": "006",
            "description": "Modelo / versão do hardware",
            "mandatoryIfExistsField": "55"
          },
          {
            "id": "007",
            "description": "Firmware do Pinpad",
            "mandatoryIfExistsField": "55"
          }
        ]
      },
      {
        "id": "90",
        "mandatory": true,
        "containsOriginalFields": [
          11,
          7,
          127
        ],
        "contains": [
          "0100"
        ]
      },
      {
        "id": "120",
        "mandatory": true,
        "containsOriginalFields": [
          11,
          7,
          127
        ]
      },
      {
        "id": "126",
        "mandatory": true,
        "subfields": [
          {
            "id": "001",
            "mandatoryIfExistsField": "52",
            "description": "Tipo de Criptografia PIN"
          },
          {
            "id": "002",
            "mandatoryIfExistsField": "52",
            "description": "KSN PIN"
          },
          {
            "id": "003",
            "mandatoryIfExistsField": "2",
            "description": "Tipo de Criptografia do Cartão"
          },
          {
            "id": "003",
            "mandatoryIfExistsField": "35",
            "description": "KSN PIN"
          },
          {
            "id": "005",
            "description": "CVV",
            "mandatoryIfNotExistsField": "55"
          }
        ]
      }
    ]
  },
  "cenario_5_id_4-preauth_subs_emv_senha_off": {
    "extendsOf": "cenario_5_id_1-preauth_subs_emv_senha_on",
    "extendsOfRemoveFields": [
      "52"
    ],
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "051"
      },
      {
        "id": "126",
        "mandatory": true,
        "subfields": [
          {
            "id": "003",
            "mandatory": true,
            "description": "Tipo de Criptografia do Cartão"
          }
        ]
      }
    ]
  },
  "cenario_5_id_7-preauth_subs_emv_sem_senha": {
    "extendsOf": "cenario_5_id_4-preauth_subs_emv_senha_off",
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "051"
      }
    ]
  },
  "cenario_5_id_10-preauth_subs_ctls_tarja_senha_on": {
    "extendsOf": "cenario_5_id_1-preauth_subs_emv_senha_on",
    "extendsOfRemoveFields": [
      "55"
    ],
    "notExistsFields": [
      "55"
    ],
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "911"
      }
    ]
  },
  "cenario_5_id_13-preauth_subs_ctls_tarja_senha_off": {
    "extendsOf": "cenario_5_id_4-preauth_subs_emv_senha_off",
    "extendsOfRemoveFields": [
      "52",
      "55"
    ],
    "notExistsFields": [
      "55"
    ],
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "911"
      },
      {
        "id": "126",
        "mandatory": true,
        "subfields": [
          {
            "id": "003",
            "mandatory": true,
            "description": "Tipo de Criptografia do Cartão"
          }
        ]
      }
    ]
  },
  "cenario_5_id_16-preauth_subs_ctls_tarja_sem_senha": {
    "extendsOf": "cenario_5_id_13-preauth_subs_ctls_tarja_senha_off"
  },
  "cenario_5_id_19-preauth_subs_ctls_chip_senha_on": {
    "extendsOf": "cenario_5_id_1-preauth_subs_emv_senha_on",
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "071"
      }
    ]
  },
  "cenario_5_id_22-preauth_subs_ctls_chip_senha_off": {
    "extendsOf": "cenario_5_id_4-preauth_subs_emv_senha_off",
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "071"
      }
    ]
  },
  "cenario_5_id_25-preauth_subs_ctls_chip_sem_senha": {
    "extendsOf": "cenario_5_id_22-preauth_subs_ctls_chip_senha_off"
  },
  "cenario_5_id_28-preauth_subs_tarja_senha_on": {
    "extendsOf": "cenario_5_id_10-preauth_subs_ctls_tarja_senha_on",
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "021"
      }
    ]
  },
  "cenario_5_id_31-preauth_subs_tarja_senha_off": {
    "extendsOf": "cenario_5_id_13-preauth_subs_ctls_tarja_senha_off",
    "fields": [
      {
        "id": "22",
        "mandatory": true,
        "regex": "021"
      }
    ]
  },
  "cenario_5_id_34-preauth_subs_tarja_sem_senha": {
    "extendsOf": "cenario_5_id_31-preauth_subs_tarja_senha_off"
  }
}
```