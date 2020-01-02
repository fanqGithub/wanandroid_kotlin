package com.fanqi.wankt.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.fanqi.wankt.R
import com.fanqi.wankt.utils.toast
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    private lateinit var userNameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var loginBtnView: Button
    private lateinit var registerView: TextView
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        var actionBar = getSupportActionBar()
        actionBar?.setDisplayHomeAsUpEnabled(true)

        loginViewModel = ViewModelProviders.of(this).get(LoginViewModel::class.java)
        userNameEditText = userName
        passwordEditText = password
        loginBtnView = loginBtn
        registerView = register

        viewModelObserver()
        loginBtn.setOnClickListener {
            val userName = userNameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            if (TextUtils.isEmpty(userName)) {
                toast("用户名不能为空", this@LoginActivity)
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(password)) {
                toast("密码不能为空", this@LoginActivity)
                return@setOnClickListener
            }
            loginViewModel.login(userName, password)
        }

        register.setOnClickListener {

        }
    }

   private fun viewModelObserver() {
        loginViewModel.loginResult.observe(this, Observer {
            if (it.errorCode != 0) {
                it.errorMsg?.let { it1 -> toast(it1, this@LoginActivity) }
            } else {
                this@LoginActivity.finish()
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item)
    }

}
