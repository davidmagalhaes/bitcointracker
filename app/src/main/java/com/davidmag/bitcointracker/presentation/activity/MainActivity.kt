package com.davidmag.bitcointracker.presentation.activity

import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import com.davidmag.bitcointracker.R
import com.davidmag.bitcointracker.domain.model.Flotation
import com.davidmag.bitcointracker.presentation.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat
import java.util.*
import javax.inject.Inject
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.data.LineDataSet
import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import android.graphics.DashPathEffect


class MainActivity : BaseActivity() {

    @Inject
    lateinit var viewModel : MainViewModel

    private val formatter = DecimalFormat("0.00")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presentationComponent.inject(this)

        viewModel = initViewModel { viewModel }

        viewModel.init()

        viewModel.hasData().observe(this, Observer { hasDataResult ->
            if(hasDataResult.returnedData){
                if(!hasDataResult.data!!){
                    refreshData()
                }
            }
        })

        viewModel.stats.observe(this, Observer {
            if(it.returnedData){
                price_txt.text = formatter.format(it.data?.price)
            }
            else if(it.failed){
                internetErrorToast()
            }
        })

        viewModel.flotations.observe(this, Observer {
            if(it.returnedData){
                fillFlotationChart(it.data!!)
            }
            else if(it.failed){
                internetErrorToast()
            }
        })

        swipe_refresh_layout.setOnRefreshListener {
            refreshData()
        }
    }

    private fun refreshData(){
        viewModel.loadData().observe(this, Observer { loadDataResult ->
            if(loadDataResult.failed) {
                internetErrorToast()
            }
        })
    }

    private fun internetErrorToast(){
        Toast.makeText(
            this,
            "An error has occurred. Check your internet connection",
            Toast.LENGTH_LONG
        ).show()
    }


    private fun fillFlotationChart(flotations : List<Flotation>) {
        val values = flotations.mapIndexed { index, flotation -> Entry(
            index.toFloat(),
            flotation.price.toFloat()
        )}

        if (flotation_chart.data != null && flotation_chart.data.dataSetCount > 0) {
            val set1 = flotation_chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            flotation_chart.data.notifyDataChanged()
            flotation_chart.notifyDataSetChanged()
        } else {
            val xVals = listOf(*(1..30).toList().map { it.toString() }.toTypedArray())

            val set1 = LineDataSet(values, "Sample Data")
            set1.setDrawIcons(false)
            set1.enableDashedLine(10f, 5f, 0f)
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            set1.color = Color.DKGRAY
            set1.setCircleColor(Color.DKGRAY)
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            set1.fillColor = Color.DKGRAY

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            flotation_chart.data = data
        }

    }


}
