package htools.analyzer;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import htools.utils.Field;
import htools.validator.FieldDependencyTO;
import htools.validator.FieldValidatorTO;

public class FieldDependencyAnalyzer {

	private static final String MATCH = "match";

	private FieldDependencyAnalyzer() {
	}

	public static void analyze(List<FieldDependencyTO> fieldsDependency, Map<String, Field> fields, Field field,
			FieldValidatorTO fieldValidator) {

		if (fieldsDependency != null) {
			Iterator<FieldDependencyTO> iterator = fieldsDependency.iterator();

			while (iterator.hasNext()) {
				FieldDependencyTO fieldDependency = iterator.next();

				String checkMethod = fieldDependency.getCheckMethod();

				Field fieldToCheck = fields.get(fieldDependency.getId());

				if (fieldToCheck != null) {
					if (checkMethod.equals(MATCH)) {
						if (fieldDependency.getContent().stream()
								.anyMatch(content -> content.equals(fieldToCheck.getValue()) && field == null)) {
							System.out.println("Bit " + fieldValidator.getId()
									+ " é mandatório porque o conteudo do bit " + fieldToCheck.getId() + " bate "
									+ fieldDependency.getContent().stream()
											.filter(content -> content.equals(fieldToCheck.getValue()))
											.collect(Collectors.toList()));
						}
					} else {
						throw new InvalidCheckMethodException(checkMethod);
					}
				}
			}
		}
	}
}
