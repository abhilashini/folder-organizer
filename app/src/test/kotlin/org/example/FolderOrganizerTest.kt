import org.junit.jupiter.api.*
import java.io.File
import kotlin.test.assertTrue

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class FolderOrganizerTest {

    private lateinit var testFolder: File

    @BeforeAll
    fun setup() {
        testFolder = File("test_data")
        testFolder.mkdirs()

         // Create some dummy files
        File(testFolder, "document.pdf").writeText("dummy PDF")
        File(testFolder, "notes.txt").writeText("some notes")
        File(testFolder, "photo.jpg").writeText("jpeg content")
        File(testFolder, "weirdfile.unknown").writeText("???")
    }

    @Test
    fun `organizes files into correct folders`() {
        main(arrayOf(testFolder.path))

        // Check that files were moved
        assertTrue(File(testFolder, "Documents/document.pdf").exists())
        assertTrue(File(testFolder, "Text/notes.txt").exists())
        assertTrue(File(testFolder, "Images/photo.jpg").exists())
        assertTrue(File(testFolder, "Others/weirdfile.unknown").exists())
    }

    @AfterAll
    fun cleanup() {
        testFolder.deleteRecursively()
    }
}