package ie.dorset.student_24088.project.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import ie.dorset.student_24088.project.constant.*
import ie.dorset.student_24088.project.model.Cart
import ie.dorset.student_24088.project.model.Product
import ie.dorset.student_24088.project.model.Session.token
import ie.dorset.student_24088.project.model.User
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val httpClient = OkHttpClient.Builder().addInterceptor { chain ->
    val request = chain.request()
    if (request.headers["withToken"] == "true") {
        request.newBuilder()
            .addHeader("Authorization", "Bearer $token")
            .build()
    }
    return@addInterceptor chain.proceed(request)
}.build()

// Build the Moshi object with Kotlin adapter factory that Retrofit will be using to parse JSON
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

// Build a Retrofit object with the Moshi converter
private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()

interface OnlineShoppingApiService {
    @Headers("withToken: true")
    @GET(ALL_CATEGORIES_ENDPOINT)
    suspend fun getCategories(): List<String>

    @Headers("withToken: true")
    @GET(ALL_PRODUCTS_IN_CATEGORY_ENDPOINT)
    suspend fun getProducts(@Path("category_name") categoryName: String): List<Product>

    @Headers("withToken: true")
    @GET(ALL_PRODUCTS)
    suspend fun getProducts(): List<Product>

    @Headers("withToken: true")
    @GET(ALL_CARTS_OF_USER_ENDPOINT)
    suspend fun getCarts(@Path("user_id") userId: String): List<Cart>

    @Headers("withToken: true")
    @GET(ALL_USERS_ENDPOINT)
    suspend fun getUsers(): List<User>

    @Headers("withToken: true")
    @GET(SINGLE_USER_ENDPOINT)
    suspend fun getUser(@Path("user_id") userId: String): User

    @POST(ADD_USER_ENDPOINT)
    suspend fun signUp(@Body user: User): User

    @FormUrlEncoded
    @POST(AUTH_ENDPOINT)
    suspend fun signIn(
        @Field("username") username: String,
        @Field("password") password: String
    ): Map<String, String>

    @Headers("withToken: true")
    @PATCH(SINGLE_CART_ENDPOINT)
    suspend fun updateCart(
        @Path("cart_id") cartId: String,
        @Body cart: Cart
    ): Cart

    @Headers("withToken: true")
    @PUT(SINGLE_CART_ENDPOINT)
    suspend fun removeItemCart(
        @Path("cart_id") cartId: String,
        @Body cart: Cart
    ): Cart

    @Headers("withToken: true")
    @DELETE(SINGLE_CART_ENDPOINT)
    suspend fun deleteCart(@Path("cart_id") cartId: String): Cart
}

// Create an object that provides a lazy-initialized retrofit service
object OnlineShoppingApi {
    val retrofitService: OnlineShoppingApiService by lazy {
        retrofit.create(OnlineShoppingApiService::class.java)
    }
}