package com.accountapp.accounts.ui.profile


import android.R.color
import android.app.Dialog
import android.content.Intent
import android.content.res.ColorStateList
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.accountapp.accounts.R
import com.accountapp.accounts.base.BaseActivity
import com.accountapp.accounts.databinding.ActivityProfileBinding
import com.accountapp.accounts.ui.changePassword.ChangePasswordActivity
import com.accountapp.accounts.ui.login.LoginActivity
import com.accountapp.accounts.utils.Prefences
import com.accountapp.accounts.utils.Utility


class ProfileFragemnt : BaseActivity() {

    lateinit var binding: ActivityProfileBinding
    val mContext by lazy { this@ProfileFragemnt }

    override fun initUI() {

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)

        setToolbarWithBackIcon(binding.includedToolbar.findViewById(R.id.toolbar), "Profile")

        binding.txtName.setText("" + Prefences.getUserName(mContext!!))
        binding.mobileNo.setText("Mobile No: " + Prefences.getUserMobile(mContext!!))
        binding.emailId.setText("Email ID: " + Prefences.getUserEmailId(mContext!!))
        binding.company.setText("Company: " + Prefences.getCompany(mContext!!))
        binding.gstNo.setText("GST: " + Prefences.getGST_No(mContext!!))
        binding.addresses.setText("Address: " + Prefences.getAddress(mContext!!))
        binding.city.setText("City: " + Prefences.getCity(mContext!!))

        binding.logout.setOnClickListener {

            Prefences.resetUserData(mContext!!)
            val intent = Intent(mContext, LoginActivity::class.java)
            Utility.startActivityWithLeftToRightAnimation(mContext, intent)
            mContext!!.finishAffinity()
        }


        binding.txtChangePasword.setOnClickListener {
            Utility.startActivityWithLeftToRightAnimation(
                mContext,
                Intent(mContext, ChangePasswordActivity::class.java)
            )
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;

    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.getItemId()) {
            R.id.menu_edit -> {
                Utility.startActivityWithLeftToRightAnimation(
                    mContext,
                    Intent(mContext, EditProfileActivity::class.java)
                )
                return true
            }
            R.id.menu_chngpassword -> {
                Utility.startActivityWithLeftToRightAnimation(
                    mContext,
                    Intent(mContext, ChangePasswordActivity::class.java)
                )

                return true
            }
            R.id.menu_logout -> {
                openLogoutPopup()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    private fun openLogoutPopup() {
        val dialog = Dialog(mContext)
        dialog.setContentView(R.layout.pop_up_logout)

        val yes_btn = dialog.findViewById<View>(R.id.yes_btn) as Button
        val cancel_btn = dialog.findViewById<View>(R.id.cancel_btn) as Button

        yes_btn.setOnClickListener {
            Prefences.resetUserData(mContext!!)
            val intent = Intent(mContext, LoginActivity::class.java)
            Utility.startActivityWithLeftToRightAnimation(mContext,intent)
            mContext!!.finishAffinity()
        }
        cancel_btn.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }

//    fun editUserProfile(title: String) {
//        if (title.equals("Edit")) {
//            makeEditTextEditableOrNonEditable(true, binding.txtName)
//            binding.txtName.requestFocus()
//            binding.txtName.setSelection(binding.txtName.text.length)
//
//            makeTextViewClickableOrNonClickable(true, binding.txtName)
//
//
//        } else {
//            Utility.closeKeyboard(binding.root, mContext)
//            makeEditTextEditableOrNonEditable(false, binding.txtName)
////            callApiToSaveProfile()
//
//            makeTextViewClickableOrNonClickable(false, binding.txtName)
//        }
//    }

//    private fun makeEditTextEditableOrNonEditable(isEditable: Boolean, editText: EditText) {
//        editText.isClickable = isEditable
//        editText.isFocusable = isEditable
//        editText.isFocusableInTouchMode = isEditable
//
//
//    }
//    private fun makeTextViewClickableOrNonClickable(isClickable: Boolean, textView: TextView) {
//        textView.isClickable = isClickable
//        textView.isEnabled = isClickable
//    }


}
