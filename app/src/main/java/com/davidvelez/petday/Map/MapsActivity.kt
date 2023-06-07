package com.davidvelez.petday.Map

import android.Manifest
import android.content.ContentProviderClient
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

import android.content.Intent
import android.net.Uri
import android.location.Geocoder
import android.location.Address

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.davidvelez.petday.R
import com.davidvelez.petday.databinding.ActivityMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private  lateinit var fusedLocation: FusedLocationProviderClient
    private lateinit var binding: ActivityMapsBinding
    val db = FirebaseFirestore.getInstance()

    companion object{
        const val REQUEST_CODE_LOCATION = 0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        fusedLocation = LocationServices.getFusedLocationProviderClient(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {

        val usuariosRef = db.collection("users")


        mMap = googleMap
        //enableLocation()
        //val direccion = "Carrera 20 #5-37, Lorica, Córdoba"
        //val direccionUri = Uri.parse("geo:0,0?q=$direccion")

        permiso()


        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true

        fusedLocation.lastLocation.addOnSuccessListener { location ->
            if (location != null){
                val ubicacion = LatLng(location.latitude, location.longitude)
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacion,15f))
            }

        }


        usuariosRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot) {
                    val usuarioId = document.id
                    val serviciosRef = usuariosRef.document(usuarioId).collection("Services")

                    serviciosRef.get()
                        .addOnSuccessListener { serviciosSnapshot ->
                            for (servicioDocument in serviciosSnapshot) {
                                val direccion = servicioDocument.getString("direcion")
                                // Haz algo con la dirección obtenida
                                val description =servicioDocument.getString("description")

                                val cost = servicioDocument.get("cost")
                                //println("direcion: $direccion")
                                //val direccion = "Cl. 65 #55-30, La Candelaria, Medellín, La Candelaria, Medellín, Antioquia"
                                val coordenadas = obtenerCoordenadasDesdeDireccion(direccion.toString())

                                if (coordenadas != null ) {
                                    val latitud = coordenadas.first
                                    val longitud = coordenadas.second
                                    val sydney = LatLng(latitud, longitud)

                                    mMap.addMarker(MarkerOptions()
                                        .position(sydney)
                                        .title(description)
                                        .snippet(cost.toString())


                                    )?.showInfoWindow()
                                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney,12f))
                                    //val torres = LatLng(6.2638937894253806,-75.56812734655378)
                                    //mMap.addMarker(MarkerOptions()
                                    //    .position(torres)
                                    //    .title("Pasear a michi")
                                    //    .snippet("10 mil pesos")


                                   // )?.showInfoWindow()
                                } else {
                                    // No se pudo obtener las coordenadas para la dirección dada
                                }








                            }
                        }
                        .addOnFailureListener { exception ->
                            // Maneja el error en caso de que ocurra
                            println("Error al obtener los servicios: $exception")
                        }
                }
            }
            .addOnFailureListener { exception ->
                // Maneja el error en caso de que ocurra
                println("Error al obtener los usuarios: $exception")
            }






        //val direccion = "Cl. 65 #55-30, La Candelaria, Medellín, La Candelaria, Medellín, Antioquia"




        // Add a marker in Sydney and move the camera
        //val sydney = LatLng(-34.0, 151.0)
        //mMap.addMarker(mMap.)

        //val udea = LatLng(6.2691952,-75.5700196)
        //mMap.addMarker(MarkerOptions()
        //    .position(udea)
        //    .title("Udea")
         //   .snippet("Ala Mater")

        //)

        //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(udea,15.0F))
        /*if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        mMap.isMyLocationEnabled = true

         */
    }
/*
    private fun  isPermiso()=ContextCompat.checkSelfPermission(
        this,Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED

    private fun enableLocation(){
        if (!::mMap.isInitialized)return
        if (isPermiso()){
            mMap.isMyLocationEnabled = true
        }else{
            requesPermiso()
        }
    }

    private fun requesPermiso() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_FINE_LOCATION)){
            Toast.makeText(this, "ve ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                REQUEST_CODE_LOCATION)
        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            REQUEST_CODE_LOCATION -> if(grantResults.isNotEmpty() && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                mMap.isMyLocationEnabled = true
            }else{
                Toast.makeText(this, "Para activar la localización ve a ajustes y acepta los permisos", Toast.LENGTH_SHORT).show()
            }
            else -> {}
        }
    }


*/
    fun permiso(){
    if (ActivityCompat.checkSelfPermission(this,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )!= PackageManager.PERMISSION_GRANTED
    ){
        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),1
        )
        return
    }
    }

    fun obtenerCoordenadasDesdeDireccion(direccion: String): Pair<Double, Double>? {
        val geocoder = Geocoder(this)
        val addressList: List<Address>?

        try {
            addressList = geocoder.getFromLocationName(direccion, 1)

            if (addressList != null && addressList.isNotEmpty()) {
                val latitude = addressList[0].latitude
                val longitude = addressList[0].longitude
                return Pair(latitude, longitude)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return null
    }


}