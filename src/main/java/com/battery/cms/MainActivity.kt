package com.battery.cms

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction
import com.battery.cms.model.UserModel
import com.battery.cms.view.HomeFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    //뒤로가기 연속 클릭 대기 시간
    var mBackWait:Long = 0

    private var mAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        // 홈화면
        supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment).commit()
        
        // 아래메뉴클릭 이벤트
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        logout.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            finish()
        }
    }

    private val HomeFragment : HomeFragment by lazy {
        HomeFragment()
    }


    // 뒤로가기 버튼 클릭
    override fun onBackPressed() {
        if(System.currentTimeMillis() - mBackWait >=2000 ) {
            mBackWait = System.currentTimeMillis()
            Toast.makeText(this, "뒤로가기 버튼을 한번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show()
        } else {
            //finish() //액티비티 종료
            finishAffinity()
        }
    }

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                Toast.makeText(this, "홈", Toast.LENGTH_SHORT).show()
                supportFragmentManager.beginTransaction().replace(R.id.container, HomeFragment()).commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu1 -> {
                Toast.makeText(this, "고객관리", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu2 -> {
                Toast.makeText(this, "재고관리", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu3 -> {
                Toast.makeText(this, "채팅", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_menu4 -> {
                Toast.makeText(this, "알림", Toast.LENGTH_SHORT).show()
                return@OnNavigationItemSelectedListener true
            }
        }


        false
    }

}