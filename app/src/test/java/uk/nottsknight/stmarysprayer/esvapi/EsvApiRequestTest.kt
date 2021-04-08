package uk.nottsknight.stmarysprayer.esvapi

import com.android.volley.NetworkResponse
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.impl.annotations.MockK
import org.junit.Before
import org.junit.Test

class EsvApiRequestTest {
    @MockK
    private lateinit var goodNetworkResponse: NetworkResponse

    @Before
    fun setUp() {
        MockKAnnotations.init(this, relaxUnitFun = true)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `Given a Request, When constructing with 0 passages, Then throw an exception`() {
        EsvApiRequest(listOf(), listOf(), { }, { })
    }

    @Test
    fun `Given a Request, When getting headers, Then return Authorization and nothing else`() {
        val request = EsvApiRequest(listOf("John 1:1"), listOf(), { }, { })
        assertThat(request.headers).hasSize(1)
        assertThat(request.headers).containsKey("Authorization")
    }

    @Test
    fun `Given a Request, When passing GET params, Then the URL is built correctly`() {
        val request = EsvApiRequest(
            listOf("Jn1:1"),
            listOf("include-verse-numbers" to true, "show-headings" to false),
            {},
            {})

        assertThat(request.url).isEqualTo("https://api.esv.org/v3/passage/text?q=Jn1:1&include-verse-numbers=true&show-headings=false")
    }
}