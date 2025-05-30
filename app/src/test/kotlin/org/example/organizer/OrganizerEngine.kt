package organizer

import org.junit.jupiter.api.*
import java.io.File
import java.nio.file.Files
import kotlin.io.path.createTempDirectory

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class OrganizerEngineTest {

    private lateinit var tempDir: File

    @BeforeEach
    fun setup() {
        tempDir = createTempDirectory("organizer-test").toFile()

        // Create test files with different extensions
        File(tempDir, "doc.pdf").writeText("PDF")
        File(tempDir, "image.jpg").writeText("JPG")
        File(tempDir, "archive.zip").writeText("ZIP")
        File(tempDir, "unknown.xyz").writeText("Unknown")
    }

    @AfterEach
    fun teardown() {
        tempDir.deleteRecursively()
    }

    @Test
    fun `organize should categorize files correctly`() {
        OrganizerEngine.organize(tempDir)

        Assertions.assertAll(
            { Assertions.assertTrue(File(tempDir, "Documents/doc.pdf").exists()) },
            { Assertions.assertTrue(File(tempDir, "Images/image.jpg").exists()) },
            { Assertions.assertTrue(File(tempDir, "Archives/archive.zip").exists()) },
            { Assertions.assertTrue(File(tempDir, "Others/unknown.xyz").exists()) }
        )
    }

    @Test
    fun `dry run should not move files`() {
        OrganizerEngine.organize(tempDir, dryRun = true)

        listOf("doc.pdf", "image.jpg", "archive.zip", "unknown.xyz").forEach {
            Assertions.assertTrue(File(tempDir, it).exists(), "$it should not be moved")
        }
    }

    @Test
    fun `undo should restore moved files`() {
        OrganizerEngine.organize(tempDir)
        OrganizerEngine.undo(tempDir)

        listOf("doc.pdf", "image.jpg", "archive.zip", "unknown.xyz").forEach {
            Assertions.assertTrue(File(tempDir, it).exists(), "$it should be back in root after undo")
        }

        Assertions.assertFalse(File(tempDir, ".organizer_log").exists(), "Log file should be deleted after undo")
    }

    @Test
    fun `organize with no files should do nothing`() {
        tempDir.deleteRecursively()
        tempDir = createTempDirectory("organizer-empty").toFile()

        OrganizerEngine.organize(tempDir)
        Assertions.assertEquals(0, tempDir.listFiles()?.size ?: -1)
    }

    @Test
    fun `undo with no log file should not crash`() {
        val noLogDir = createTempDirectory("organizer-nolog").toFile()
        Assertions.assertDoesNotThrow {
            OrganizerEngine.undo(noLogDir)
        }
    }
}