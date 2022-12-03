package de.fruxz.ascend.tool.data

/**
 * This object provides methods to convert a CSV file to a list of arrays of strings.
 * @author Fruxz
 * @since 1.0
 */
@Deprecated("Ascends CSV solution is very rudimentary and should be replaced by a better solution in the future.")
object CSV {

	/**
	 * Basically converts a CSV content to a list of arrays of strings.
	 * @param csvContent The CSV content to convert.
	 * @author Fruxz
	 * @since 1.0
	 */
	fun convertCSV(csvContent: String): List<Array<String>> = buildList {
		csvContent.lines().forEach {
			add(it.split(";").toTypedArray())
		}
	}

	@Suppress("DEPRECATION") // We are currently inside the deprecated api, no need of extra compiler warnings.
	fun convertToTabularData(csvContent: String) = TabularData(csvContent)

}