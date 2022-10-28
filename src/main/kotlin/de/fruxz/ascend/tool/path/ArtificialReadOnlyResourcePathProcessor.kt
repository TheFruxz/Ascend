package de.fruxz.ascend.tool.path

import de.fruxz.ascend.extension.getResourceByteArray
import java.io.File

/**
 * This object represents the Ascend provided [ArtificialPathProcessor],
 * to easily get files from the resources.
 * @author Fruxz
 * @since 1.0
 */
object ArtificialReadOnlyResourcePathProcessor : ArtificialPathProcessor {

    override val protocolName = "readOnlyResource"

    override val processFile: (String) -> File = {
        val tempFile = File.createTempFile(it.split(".").last(), null)

        tempFile.writeBytes(getResourceByteArray(it))

        tempFile
    }

}