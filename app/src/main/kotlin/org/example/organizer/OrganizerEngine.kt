package organizer

import java.io.File

object OrganizerEngine {
    private val extensionToFolder = mapOf(
        "pdf" to "Documents",
        "docx" to "Documents",
        "jpg" to "Images",
        "jpeg" to "Images",
        "png" to "Images",
        "gif" to "Images",
        "mp4" to "Videos",
        "mkv" to "Videos",
        "txt" to "Text",
        "zip" to "Archives"
    )

    private const val LOG_FILE_NAME = ".organizer_log"

    fun organize(folder: File, dryRun: Boolean = false) {
        val moveLog = mutableListOf<Pair<File, File>>()

        folder.listFiles()?.forEach { file ->
            if (file.isFile) {
                val ext = file.extension.lowercase()
                val targetFolder = File(folder, extensionToFolder[ext] ?: "Others")
                val targetFile = File(targetFolder, file.name)

                if (dryRun) {
                    println("[Dry Run] Would move: ${file.name} → ${targetFolder.name}/")
                } else {
                    targetFolder.mkdirs()
                    val moved = file.renameTo(targetFile)
                    if (moved) {
                        moveLog.add(file to targetFile)
                        println("Moved: ${file.name} → ${targetFolder.name}/")
                    }
                }
            }
        }

        if (!dryRun && moveLog.isNotEmpty()) {
            writeLog(folder, moveLog)
            println("Done. Log saved to $LOG_FILE_NAME")
        }
    }

    fun undo(folder: File) {
        val logFile = File(folder, LOG_FILE_NAME)
        if (!logFile.exists()) {
            println("No previous move log found.")
            return
        }

        logFile.readLines().forEach { line ->
            val (fromPath, toPath) = line.split("|")
            val fromFile = File(fromPath)
            val toFile = File(toPath)

            if (fromFile.exists()) {
                fromFile.renameTo(toFile)
                println("Undid: ${fromFile.name} → ${toFile.name}")
            }
        }

        logFile.delete()
        println("Undo complete.")
    }

    private fun writeLog(folder: File, moves: List<Pair<File, File>>) {
        val logFile = File(folder, LOG_FILE_NAME)
        logFile.printWriter().use { out ->
            for ((_, dest) in moves) {
                val originalPath = File(folder, dest.name).absolutePath
                out.println("${dest.absolutePath}|$originalPath")
            }
        }
    }
}
