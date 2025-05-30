import java.io.File

fun main(args: Array<String>) {
    if (args.isEmpty()) {
        println("Usage: java -jar FolderOrganizer.jar <folder_path>")
        return
    }

    var folder = File(args[0])
    if (!folder.exists() || !folder.isDirectory) {
        println("Provided path is not a valid directory.")
        return
    }

    val extensionToFolder = mapOf(
        "pdf" to "Documents",
        "docx" to "Documents",
        "jpg" to "Images",
        "png" to "Images",
        "mp4" to "Videos",
        "txt" to "Text",
        "zip" to "Archives"
    )

    folder.listFiles()?.forEach { file -> 
        if(file.isFile) {
            val ext = file.extension.lowercase()
            val targetFolder = File(folder, extensionToFolder[ext] ?: "Others")
            targetFolder.mkdirs()
            val moved = file.renameTo(File(targetFolder, file.name))
            if (moved) println("Moved: ${file.name} → ${targetFolder.name}/")
        }
    }

    println("Done organizing '${folder.name}'")
}