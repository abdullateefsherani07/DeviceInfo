package com.abdul.android.deviceinfo.home.battery

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.abdul.android.deviceinfo.home.DeviceDetailsRepository
import com.abdul.android.deviceinfo.models.UserDeviceDetailsProperty
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DeviceBatteryDetailsRepository(private val context: Context): DeviceDetailsRepository() {

    suspend fun getBatteryDetails(): List<UserDeviceDetailsProperty?> {
        return withContext(Dispatchers.IO){
            val batteryDetails = mutableListOf<UserDeviceDetailsProperty?>(null)
            try {
                val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

                val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
                val batteryStatusIntent: Intent? = context.registerReceiver(null, intentFilter)
                val healthValue = when(batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_HEALTH, BatteryManager.BATTERY_HEALTH_UNKNOWN)) {
                    BatteryManager.BATTERY_HEALTH_GOOD -> "Good"
                    BatteryManager.BATTERY_HEALTH_UNKNOWN -> "Unknown"
                    BatteryManager.BATTERY_HEALTH_OVERHEAT -> "Overheat"
                    BatteryManager.BATTERY_HEALTH_DEAD -> "Dead"
                    BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "Over Voltage"
                    BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "Unspecified Failure"
                    else -> "Unknown"
                }
                val health = UserDeviceDetailsProperty("Health", healthValue)
                batteryDetails.add(health)
                val levelValue = batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_LEVEL, 0) ?: 0
                val level = UserDeviceDetailsProperty("Level", levelValue.toString())
                batteryDetails.add(level)
                val statusValue = when(batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_STATUS, BatteryManager.BATTERY_STATUS_UNKNOWN)) {
                    BatteryManager.BATTERY_STATUS_CHARGING -> "Charging"
                    BatteryManager.BATTERY_STATUS_DISCHARGING -> "Discharging"
                    BatteryManager.BATTERY_STATUS_NOT_CHARGING -> "Not Charging"
                    BatteryManager.BATTERY_STATUS_FULL -> "Full Charged"
                    else -> "Unknown"
                }
                val status = UserDeviceDetailsProperty("Status", statusValue)
                batteryDetails.add(status)
                val powerSourceValue = when(batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0)) {
                    BatteryManager.BATTERY_PLUGGED_AC -> "AC"
                    else -> "Battery"
                }
                val powerSource = UserDeviceDetailsProperty("Power Source", powerSourceValue)
                batteryDetails.add(powerSource)
                val technologyValue = batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_TECHNOLOGY, 0) ?: ""
                val technology = UserDeviceDetailsProperty("Technology", technologyValue.toString())
                batteryDetails.add(technology)
                val temperatureValue = batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) ?: 0
                val temperature = UserDeviceDetailsProperty("Temperature", temperatureValue.toString())
                batteryDetails.add(temperature)
                val voltageValue = batteryStatusIntent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) ?: 0
                val voltage = UserDeviceDetailsProperty("Voltage", voltageValue.toString())
                batteryDetails.add(voltage)
                batteryDetails
            } catch (e: Exception){
                Log.e("DeviceBatteryDetailsRepository", e.message!!)
                batteryDetails
            }
        }
    }
}