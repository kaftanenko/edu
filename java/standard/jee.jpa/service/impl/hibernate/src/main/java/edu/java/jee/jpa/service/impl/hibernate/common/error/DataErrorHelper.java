package edu.java.jee.jpa.service.impl.hibernate.common.error;

public class DataErrorHelper {

	// ... business methods

	public static DataNotFoundRuntimeException handleDataNotFoundException(final Class<?> dataType,
			final String dataTypeIdPropName, final long dataObjectId) {

		throw new DataNotFoundRuntimeException(dataType, dataTypeIdPropName, dataObjectId);
	}

	public static DataNotFoundRuntimeException handleDataNotFoundException(final Class<?> dataType,
			final long dataObjectId, final String dataObjectPropNotFoundInfo) {

		final String dataTypeIdPropName = "id";
		throw new DataNotFoundRuntimeException(dataType, dataTypeIdPropName, dataObjectId, dataObjectPropNotFoundInfo);
	}

	public static DataNotFoundRuntimeException handleDataNotFoundException(final Class<?> dataType,
			final String dataTypeIdPropName, final long dataObjectId, final String dataObjectPropNotFoundInfo) {

		throw new DataNotFoundRuntimeException(dataType, dataTypeIdPropName, dataObjectId, dataObjectPropNotFoundInfo);
	}

}
