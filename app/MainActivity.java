class MainActivity : AppCompatActivity() {
    private val TAG = "유저 사용자 정보에 대한 메세지"



    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)