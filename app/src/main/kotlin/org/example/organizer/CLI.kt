package organizer

object CLI {
    fun showHelp() {
        println(
            """
            Usage: java -jar FolderOrganizer-fat.jar <folder_path> [--dry-run | --undo | --help]

            Options:
              --dry-run   Show what would be moved without making changes
              --undo      Revert the last file organization using the log
              --help      Show this help message
            """.trimIndent()
        )
    }
}