package organizer

import org.junit.jupiter.api.*
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertTrue

class CLITest {

    private val originalOut = System.out
    private lateinit var outContent: ByteArrayOutputStream

    @BeforeEach
    fun setUp() {
        outContent = ByteArrayOutputStream()
        System.setOut(PrintStream(outContent))
    }

    @AfterEach
    fun tearDown() {
        System.setOut(originalOut)
    }

    @Test
    fun `showHelp prints usage instructions`() {
        CLI.showHelp()
        val output = outContent.toString()
        assertTrue(output.contains("Usage:"), "Help output should contain usage")
        assertTrue(output.contains("--dry-run"), "Help should mention --dry-run")
        assertTrue(output.contains("--undo"), "Help should mention --undo")
        assertTrue(output.contains("--help"), "Help should mention --help")
    }
}