package edu.java.jee.jpa.service.impl.hibernate.common.error;

public class DataNotFoundRuntimeException extends RuntimeException {

	// .. constants

	private static final long serialVersionUID = 1L;

	// ... constructors

	public DataNotFoundRuntimeException(final String message) {

		super(message);
	}

	public DataNotFoundRuntimeException(final Class<?> dataType, final String dataTypeIdPropName,
			final long dataObjectId) {

		super(buildErrorMessage(dataType, dataTypeIdPropName, dataObjectId));
	}

	public DataNotFoundRuntimeException(final Class<?> dataType, final String dataTypeIdPropName,
			final long dataObjectId, final String dataObjectPropNotFoundInfo) {

		super(buildErrorMessage(dataType, dataTypeIdPropName, dataObjectId, dataObjectPropNotFoundInfo));
	}

	// ... helper methods

	private static String buildErrorMessage(final Class<?> dataType, final String dataTypeIdPropName,
			final long dataObjectId) {

		final StringBuilder sb = new StringBuilder();

		sb.append("Datenobjekt ");
		sb.append(dataType.getSimpleName());
		sb.append("[");
		sb.append(dataTypeIdPropName);
		sb.append(":");
		sb.append(dataObjectId);
		sb.append("] konnte nicht gefunden werden.");

		return sb.toString();
	}

	private static String buildErrorMessage(final Class<?> dataType, final String dataTypeIdPropName,
			final long dataObjectId, final String dataObjectPropNotFoundInfo) {

		final StringBuilder sb = new StringBuilder();

		sb.append("Datenobjekt ");
		sb.append(dataType.getSimpleName());
		sb.append("[");
		sb.append(dataTypeIdPropName);
		sb.append(":");
		sb.append(dataObjectId);
		sb.append("] beinhaltet kein untegeordnetes Datenobjekt ");
		sb.append(dataObjectPropNotFoundInfo);
		sb.append(".");
		sb.append(" Das bedeutet, es dürfte kein solches Objekt angefordert werden.");
		sb.append(
				" Nutzen Sie bitte künftig entsprechende has-Methode für eine \"in Vorfeld\"-Überprüfung von so einem Fall.");

		return sb.toString();
	}

}
