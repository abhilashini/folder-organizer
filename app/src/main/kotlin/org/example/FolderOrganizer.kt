import java.io.File
import organizer.CLI
import organizer.OrganizerEngine

fun main(args: Array<String>) {
    if (args.isEmpty() || args.contains("--help")) {
        CLI.showHelp()
        return
    }

    val folderPath = args.firstOrNull { !it.startsWith("--") }
    if (folderPath == null) {
        println("Please specify a folder path.")
        CLI.showHelp()
        return
    }

    val folder = File(folderPath)
    if (!folder.exists() || !folder.isDirectory) {
        println("'$folderPath' is not a valid folder.")
        return
    }

    when {
        args.contains("--dry-run") -> OrganizerEngine.organize(folder, dryRun = true)
        args.contains("--undo")    -> OrganizerEngine.undo(folder)
        else                       -> OrganizerEngine.organize(folder)
    }
}
