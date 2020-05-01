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
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.renderer.XAxisRenderer


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
                flotation_chart.refreshDrawableState()
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
            if(loadDataResult.isSuccessful()){
                swipe_refresh_layout.isRefreshing = false
            }
            else if(loadDataResult.failed) {
                swipe_refresh_layout.isRefreshing = false
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
            index.toFloat() + 1,
            flotation.price.toFloat(),
            flotation.date.dayOfMonth.toString()
        )}

//        if(flotation_chart.data != null){
//            flotation_chart.clearValues()
//        }

        if (flotation_chart.data != null && flotation_chart.data.dataSetCount > 0) {
            val set1 = flotation_chart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            flotation_chart.data.notifyDataChanged()
            flotation_chart.notifyDataSetChanged()
        } else if(values.isNotEmpty()) {
            val xVals = arrayOf(*(1..30).toList().map { it.toFloat() }.toTypedArray())


            val set1 = LineDataSet(values, "Sample Data")
            set1.setDrawIcons(false)
            set1.enableDashedLine(10f, 5f, 0f)
            set1.enableDashedHighlightLine(10f, 5f, 0f)
            set1.color = resources.getColor(R.color.colorAccent)
            set1.setCircleColor(resources.getColor(R.color.colorAccent))
            set1.lineWidth = 1f
            set1.circleRadius = 3f
            set1.setDrawCircleHole(false)
            set1.valueTextSize = 9f
            set1.setDrawFilled(true)
            set1.formLineWidth = 1f
            set1.formLineDashEffect = DashPathEffect(floatArrayOf(10f, 5f), 0f)
            set1.formSize = 15f
            set1.fillColor = resources.getColor(R.color.colorAccent)
            set1.valueFormatter = object : ValueFormatter() {
                override fun getPointLabel(entry: Entry?): String {
                    return entry?.data as String
                }
            }

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)
            val data = LineData(dataSets)
            flotation_chart.data = data

            flotation_chart.axisRight.isEnabled = false
            flotation_chart.description.isEnabled = false
            flotation_chart.legend.isEnabled = false
            flotation_chart.xAxis.mEntries = xVals.toFloatArray()
            flotation_chart.xAxis.axisMinimum = 1.0F
            flotation_chart.xAxis.axisMaximum = values.last().x
            flotation_chart.setTouchEnabled(false)
            flotation_chart.isDragEnabled = false
            flotation_chart.setScaleEnabled(false)
            flotation_chart.isScaleXEnabled = false
            flotation_chart.isScaleYEnabled = false
            flotation_chart.setPinchZoom(false)
            flotation_chart.isDoubleTapToZoomEnabled = false
            flotation_chart.setDrawGridBackground(false)
            flotation_chart.xAxis.setDrawGridLines(false)
            flotation_chart.axisLeft.setDrawGridLines(false)

            flotation_chart.notifyDataSetChanged()
        }

        flotation_chart.invalidate()
    }


}
