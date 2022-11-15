package com.nightstalker.artic.features.qrcode

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.nightstalker.artic.R
import com.nightstalker.artic.features.qrcode.QrConstants.QR_BORDER_STROKE_WIDTH
import kotlinx.android.synthetic.main.fragment_qrscanner.view.containerScanner
import kotlinx.android.synthetic.main.fragment_qrscanner.view.flashToggle
import me.dm7.barcodescanner.zbar.Result
import me.dm7.barcodescanner.zbar.ZBarScannerView

class QRScannerFragment : Fragment(), ZBarScannerView.ResultHandler {

    companion object {

        fun newInstance(): QRScannerFragment {
            return QRScannerFragment()
        }
    }

    private lateinit var mView: View

    lateinit var scannerView: ZBarScannerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mView = inflater.inflate(R.layout.fragment_qrscanner, container, false)
        initializeQRCamera()
        onClicks()
        return mView.rootView
    }


    private fun initializeQRCamera() {
        val lContext = context
        if(lContext != null) {
            scannerView = ZBarScannerView(context)
            scannerView.setResultHandler(this)
            scannerView.setBackgroundColor(ContextCompat.getColor(lContext, R.color.colorTranslucent))
            scannerView.setBorderColor(ContextCompat.getColor(lContext, R.color.colorPrimaryDark))
            scannerView.setLaserColor(ContextCompat.getColor(lContext, R.color.colorPrimaryDark))
            scannerView.setBorderStrokeWidth(QR_BORDER_STROKE_WIDTH)
            scannerView.setSquareViewFinder(true)
            scannerView.setupScanner()
            scannerView.setAutoFocus(true)
            startQRCamera()
            mView.containerScanner.addView(scannerView)
        }
    }

    private fun startQRCamera() {
        scannerView.startCamera()
    }

    private fun onClicks() {
        mView.flashToggle.setOnClickListener {
            if (mView.flashToggle.isSelected) {
                offFlashLight()
            } else {
                onFlashLight()
            }
        }
    }

    private fun onFlashLight() {
        mView.flashToggle.isSelected = true
        scannerView.flash = true
    }

    private fun offFlashLight() {
        mView.flashToggle.isSelected = false
        scannerView.flash = false
    }

    override fun onResume() {
        super.onResume()
        scannerView.setResultHandler(this)
        scannerView.startCamera()
    }

    override fun onPause() {
        super.onPause()
        scannerView.stopCamera()
    }

    override fun onDestroy() {
        super.onDestroy()
        scannerView.stopCamera()
    }

    override fun handleResult(rawResult: Result?) {
        Toast.makeText(context, rawResult?.contents, Toast.LENGTH_LONG).show()
        scannerView.resumeCameraPreview(this)
    }

}