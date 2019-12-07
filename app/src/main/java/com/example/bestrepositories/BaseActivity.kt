
package com.example.bestrepositories

import androidx.appcompat.app.AppCompatActivity
import android.widget.ProgressBar
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.example.viewmodel.base.BaseViewModel
import org.kodein.di.KodeinAware
import java.util.*

open class BaseActivity : AppCompatActivity(), KodeinAware {


    private val loadingStack by lazy { Stack<Boolean>() }

    private val loadingDialog by lazy { LoadingDialog(this) }

    private var viewModels: Array<out BaseViewModel>? = null

    override val kodein by lazy { (applicationContext as App).kodein }

    override fun onDestroy() {
        super.onDestroy()
        viewModels?.forEach {
            it.clear()
        }
        viewModels = null
    }

    fun setupBaseViewModels(vararg baseViewModels: BaseViewModel) {
        viewModels = baseViewModels

        baseViewModels.forEach {
            setupLoading(it.loading)
            setupError(it.error)
        }
    }

    private fun setupLoading(loading: LiveData<Boolean>) {
        loading.observe(this, Observer {
            if (it) startLoading()
            else stopLoading()
        })
    }

    open fun startLoading() {
        if (!isFinishing) {
            loadingDialog.show()
            loadingStack.add(true)
        }
    }

    open fun stopLoading() {
        if (loadingStack.isNotEmpty())
            loadingStack.pop()
        if (loadingStack.isEmpty()) loadingDialog.dismiss()
    }

    protected open fun setupError(error: LiveData<ErrorUiModel>) {
        error.observe(this, Observer {
            onError(it)
        })
    }

    open fun onError(errorUiModel: ErrorUiModel) {
        when (errorUiModel.error) {
            is Error -> {
                DialogUtils.createInformativeDialog(
                    activity = this,
                    title = getString(R.string.dialog_error_title),
                    text = errorUiModel.error.message,
                    buttonText = errorUiModel.positiveText,
                    onPositiveClick = {
                        errorUiModel.onPositiveClick()
                        if (errorUiModel.finishActivity) {
                            this@BaseActivity.finish()
                        }
                    })
            }
            is Error.UnknownException -> {
                DialogUtils.createInformativeDialog(
                    activity = this,
                    title = getString(R.string.dialog_error_title),
                    text = getString(R.string.error_message_unknown),
                    buttonText = errorUiModel.positiveText,
                    onPositiveClick = errorUiModel.onPositiveClick
                )
            }
        }
    }
}
