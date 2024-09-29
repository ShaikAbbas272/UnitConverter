package com.example.unitconverter

import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

//    #,###.####

    private val decimalFormat = DecimalFormat("#,###.####") // Updated pattern to avoid scientific notation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val categorySpinner: Spinner = findViewById(R.id.categorySpinner)
        val inputValue: EditText = findViewById(R.id.inputValue)
        val fromSpinner: Spinner = findViewById(R.id.fromSpinner)
        val toSpinner: Spinner = findViewById(R.id.toSpinner)
        val convertButton: Button = findViewById(R.id.convertButton)
        val formulaTextView: TextView = findViewById(R.id.formulaTextView)
        val resultTextView: TextView = findViewById(R.id.resultTextView)

        // Define conversion categories
        val categories = arrayOf(
            "Area", "Data Transfer Rate", "Digital Storage", "Energy", "Frequency",
            "Fuel Economy", "Length", "Mass", "Plane Angle", "Pressure",
            "Speed", "Temperature", "Time", "Volume"
        )

        // Define unit mappings for each category
        val unitsMapping = mapOf(
            "Area" to arrayOf(
                "Square Meters", "Square Kilometers", "Square Miles", "Square Feet",
                "Square Yards", "Acres", "Hectares","Square Centimeters","Square Millimeters",
                "Ares","Roods"
            ),
            "Data Transfer Rate" to arrayOf(
                "Bits per second", "Kilobits per second", "Megabits per second",
                "Gigabits per second", "Terabits per second","Bytes per second","Kilobytes per second",
                "Megabytes per second","Gigabytes per second","Terabytes per second","Bits per minute","Bits per hour",
                "Bits per day","Kilobits per minute","Kilobits per hour","Kilobits per day"
            ),
            "Digital Storage" to arrayOf(
                "Bits", "Bytes", "Kilobytes", "Megabytes", "Gigabytes", "Terabytes","Petabytes",
                "Exabytes","Zettabytes","Yottabytes","Nibbles","Words"
            ),
            "Energy" to arrayOf(
                "Joules", "Calories", "Kilocalories", "Watt-hours", "Kilowatt-hours",
                "British Thermal Units","Electronvolts","Foot-pounds","Ergs","Kilojoules",
                "Megajoules","Gigajoules"
            ),
            "Frequency" to arrayOf(
                "Hertz", "Kilohertz", "Megahertz", "Gigahertz", "Terahertz","Millihertz","Microhertz",
                "Nanohertz","Picohertz","Femtohertz","Attohertz"
            ),
            "Fuel Economy" to arrayOf(
                "Miles per Gallon", "Kilometers per Liter", "Liters per 100 Kilometers","Liters per 100 Miles",
                "Gallons per 100 Miles","Miles per Liter","Kilometers per Gallon (US)","Kilometers per Gallon (UK)",
                "Miles per Gallon (UK)"
            ),
            "Length" to arrayOf(
                "Meters", "Kilometers", "Miles", "Feet", "Inches", "Centimeters",
                "Millimeters", "Yards","Nautical Miles","Angstroms","Parsecs","Light-Years",
                "Rods","Fathoms"
            ),
            "Mass" to arrayOf(
                "Grams", "Kilograms", "Milligrams", "Pounds", "Ounces","Tons (US)","Tons (UK)",
                "Stones","Carats","Drachmas","Quintals"
            ),
            "Plane Angle" to arrayOf(
                "Degrees", "Radians", "Gradians","Minutes","Seconds","Turns","Milliradians",
                "Centesimal Degrees"
            ),
            "Pressure" to arrayOf(
                "Pascals", "Bar", "Atmospheres", "Psi", "Torr","Millimeters of Mercury (mmHg)",
                "Inches of Mercury (inHg)","Kilopascals","Megapascals","Standard Atmospheres","Technical Atmospheres",
                "Millibars"
            ),
            "Speed" to arrayOf(
                "Meters per Second", "Kilometers per Hour", "Miles per Hour", "Feet per Second",
                "Knots","Mach","Kilometers per Minute","Miles per Minute","Inches per Second",
                "Centimeters per Second"
            ),
            "Temperature" to arrayOf(
                "Celsius", "Fahrenheit", "Kelvin", "Rankine","Delisle","Newton","Reaumur","Rømer",
                "Lloyd"
            ),
            "Time" to arrayOf(
                "Seconds", "Minutes", "Hours", "Days", "Weeks","Months","Years","Microseconds",
                "Milliseconds","Nanoseconds","Fortnights","Julian Years"
            ),
            "Volume" to arrayOf(
                "Liters", "Milliliters", "Cubic Meters", "Cubic Centimeters",
                "Gallons (US)", "Gallons (UK)", "Quarts (US)", "Pints (US)","Fluid Ounces (US)",
                "Fluid Ounces (UK)","Tablespoons (US)","Teaspoons (US)","Barrels (US)","Barrels (UK)",
                "Cubic Inches"
            )
        )

        val conversionRates = mapOf(
            "Area" to mapOf(
                "Square Meters" to 1.0,
                "Square Kilometers" to 1e-6,
                "Square Miles" to 3.861e-7,
                "Square Feet" to 10.7639,
                "Square Yards" to 1.19599,
                "Acres" to 2.471e-4,
                "Hectares" to 1e-4,
                "Square Centimeters" to 1e4,
                "Square Millimeters" to 1e6,
                "Ares" to 1e-2,
                "Roods" to 2.471e-4
            ),
            "Data Transfer Rate" to mapOf(
                "Bits per second" to 1.0,
                "Kilobits per second" to 1e-3,
                "Megabits per second" to 1e-6,
                "Gigabits per second" to 1e-9,
                "Terabits per second" to 1e-12,
                "Bytes per second" to 0.125, // 1 Byte = 8 bits
                "Kilobytes per second" to 1.25e-4, // 1 Kilobyte = 8 Kilobits
                "Megabytes per second" to 1.25e-7, // 1 Megabyte = 8 Megabits
                "Gigabytes per second" to 1.25e-10, // 1 Gigabyte = 8 Gigabits
                "Terabytes per second" to 1.25e-13, // 1 Terabyte = 8 Terabits
                "Bits per minute" to 60.0, // 1 bit per second = 60 bits per minute
                "Bits per hour" to 3600.0, // 1 bit per second = 3600 bits per hour
                "Bits per day" to 86400.0, // 1 bit per second = 86400 bits per day
                "Kilobits per minute" to 60e-3, // 1 Kilobit per second = 60 Kilobits per minute
                "Kilobits per hour" to 3600e-3, // 1 Kilobit per second = 3600 Kilobits per hour
                "Kilobits per day" to 86400e-3 // 1 Kilobit per second = 86400 Kilobits per day
            ),
            "Digital Storage" to mapOf(
                "Bits" to 1.0,
                "Bytes" to 8.0, // 1 Byte = 8 bits
                "Kilobytes" to 8e3, // 1 Kilobyte = 8,000 bits
                "Megabytes" to 8e6, // 1 Megabyte = 8,000,000 bits
                "Gigabytes" to 8e9, // 1 Gigabyte = 8,000,000,000 bits
                "Terabytes" to 8e12, // 1 Terabyte = 8,000,000,000,000 bits
                "Petabytes" to 8e15, // 1 Petabyte = 8,000,000,000,000,000 bits
                "Exabytes" to 8e18, // 1 Exabyte = 8,000,000,000,000,000,000 bits
                "Zettabytes" to 8e21, // 1 Zettabyte = 8,000,000,000,000,000,000,000 bits
                "Yottabytes" to 8e24, // 1 Yottabyte = 8,000,000,000,000,000,000,000,000 bits
                "Nibbles" to 4.0, // 1 Nibble = 4 bits
                "Words" to 16.0 // 1 Word (16-bit) = 16 bits
            ),
            "Energy" to mapOf(
                "Joules" to 1.0,
                "Calories" to 0.239006, // 1 Calorie = 0.239006 Joules
                "Kilocalories" to 0.000239006, // 1 Kilocalorie = 0.000239006 Joules
                "Watt-hours" to 0.000277778, // 1 Watt-hour = 0.000277778 Joules
                "Kilowatt-hours" to 2.77778e-7, // 1 Kilowatt-hour = 2.77778e-7 Joules
                "British Thermal Units" to 0.000947817, // 1 BTU = 0.000947817 Joules
                "Electronvolts" to 6.242e+18, // 1 Electronvolt = 6.242e+18 Joules
                "Foot-pounds" to 0.737562, // 1 Foot-pound = 0.737562 Joules
                "Ergs" to 1e+7, // 1 Erg = 1e+7 Joules
                "Kilojoules" to 1e-3, // 1 Kilojoule = 1e-3 Joules
                "Megajoules" to 1e-6, // 1 Megajoule = 1e-6 Joules
                "Gigajoules" to 1e-9 // 1 Gigajoule = 1e-9 Joules
            ),
            "Frequency" to mapOf(
                "Hertz" to 1.0,
                "Kilohertz" to 1e-3, // 1 Kilohertz = 1,000 Hertz
                "Megahertz" to 1e-6, // 1 Megahertz = 1,000,000 Hertz
                "Gigahertz" to 1e-9, // 1 Gigahertz = 1,000,000,000 Hertz
                "Terahertz" to 1e-12, // 1 Terahertz = 1,000,000,000,000 Hertz
                "Millihertz" to 1e3, // 1 Millihertz = 1/1,000 Hertz
                "Microhertz" to 1e6, // 1 Microhertz = 1/1,000,000 Hertz
                "Nanohertz" to 1e9, // 1 Nanohertz = 1/1,000,000,000 Hertz
                "Picohertz" to 1e12, // 1 Picohertz = 1/1,000,000,000,000 Hertz
                "Femtohertz" to 1e15, // 1 Femtohertz = 1/1,000,000,000,000,000 Hertz
                "Attohertz" to 1e18 // 1 Attohertz = 1/1,000,000,000,000,000,000 Hertz
            ),
            "Fuel Economy" to mapOf(
                "Miles per Gallon" to 1.0, // 1 MPG
                "Kilometers per Liter" to 0.425144, // 1 km/L = 0.425144 MPG
                "Liters per 100 Kilometers" to 235.215, // 1 L/100 km = 235.215 MPG
                "Liters per 100 Miles" to 100.0 / 62.1371, // 1 L/100 mi = 100 / 62.1371 MPG
                "Gallons per 100 Miles" to 100.0 / 62.1371 / 3.78541, // 1 gal/100 mi = 100 / (62.1371 * 3.78541) MPG
                "Miles per Liter" to 0.425144 * 1.0, // 1 MP/L = 0.425144 MPG
                "Kilometers per Gallon (US)" to 1.0 / 0.425144, // 1 km/US gal = 1 / 0.425144 MPG
                "Kilometers per Gallon (UK)" to 1.0 / 0.355214, // 1 km/UK gal = 1 / 0.355214 MPG
                "Miles per Gallon (UK)" to 1.0 / 1.20095 // 1 MPG (UK) = 1 / 1.20095 MPG (US)
            ),
            "Length" to mapOf(
                "Meters" to 1.0,
                "Kilometers" to 1e-3, // 1 Kilometer = 1,000 Meters
                "Miles" to 6.2137e-4, // 1 Mile = 1,609.34 Meters
                "Feet" to 3.28084, // 1 Foot = 0.3048 Meters
                "Inches" to 39.3701, // 1 Inch = 0.0254 Meters
                "Centimeters" to 100.0, // 1 Centimeter = 0.01 Meters
                "Millimeters" to 1000.0, // 1 Millimeter = 0.001 Meters
                "Yards" to 1.09361, // 1 Yard = 0.9144 Meters
                "Nautical Miles" to 5.3996e-4, // 1 Nautical Mile = 1,852 Meters
                "Angstroms" to 1e+10, // 1 Angstrom = 1e-10 Meters
                "Parsecs" to 3.24078e-17, // 1 Parsec = 3.086e+16 Meters
                "Light-Years" to 1.057e-17, // 1 Light-Year = 9.461e+15 Meters
                "Rods" to 19.165, // 1 Rod = 5.0292 Meters
                "Fathoms" to 1.8288 // 1 Fathom = 1.8288 Meters
            ),
            "Mass" to mapOf(
                "Grams" to 1.0,
                "Kilograms" to 1e-3, // 1 Kilogram = 1,000 Grams
                "Milligrams" to 1e+3, // 1 Milligram = 0.001 Grams
                "Pounds" to 0.00220462, // 1 Pound = 453.59237 Grams
                "Ounces" to 0.035274, // 1 Ounce = 28.3495 Grams
                "Tons (US)" to 1.10231e-6, // 1 US Ton = 907,184.74 Grams
                "Tons (UK)" to 9.842065e-7, // 1 UK Ton = 1,016,046.91 Grams
                "Stones" to 0.000157473, // 1 Stone = 6.35 Kilograms
                "Carats" to 5.0, // 1 Carat = 0.2 Grams
                "Drachmas" to 0.0533333, // 1 Drachma = 3.3333 Grams
                "Quintals" to 1e-2 // 1 Quintal = 100 Kilograms
            ),
            "Plane Angle" to mapOf(
                "Degrees" to 1.0,
                "Radians" to 0.0174533, // 1 Degree = 0.0174533 Radians
                "Gradians" to 1.0 / 0.9, // 1 Degree = 1.111111 Gradians
                "Minutes" to 60.0, // 1 Degree = 60 Minutes
                "Seconds" to 3600.0, // 1 Degree = 3,600 Seconds
                "Turns" to 0.00277778, // 1 Degree = 0.00277778 Turns
                "Milliradians" to 17.4533, // 1 Degree = 17.4533 Milliradians
                "Centesimal Degrees" to 1.11111 // 1 Degree = 1.11111 Centesimal Degrees
            ),
            "Pressure" to mapOf(
                "Pascals" to 1.0,
                "Bar" to 1e-5, // 1 Bar = 100,000 Pascals
                "Atmospheres" to 9.86923e-6, // 1 Atmosphere = 101,325 Pascals
                "Psi" to 0.000145038, // 1 Psi = 6894.76 Pascals
                "Torr" to 0.00750062, // 1 Torr = 133.322 Pascals
                "Millimeters of Mercury (mmHg)" to 0.00750062, // 1 mmHg = 133.322 Pascals
                "Inches of Mercury (inHg)" to 0.2953, // 1 inHg = 3,386.39 Pascals
                "Kilopascals" to 1e-3, // 1 Kilopascal = 1,000 Pascals
                "Megapascals" to 1e-6, // 1 Megapascal = 1,000,000 Pascals
                "Standard Atmospheres" to 9.86923e-6, // 1 Standard Atmosphere = 101,325 Pascals
                "Technical Atmospheres" to 9.86923e-6, // 1 Technical Atmosphere = 101,325 Pascals
                "Millibars" to 1e-2 // 1 Millibar = 100 Pascals
            ),
            "Speed" to mapOf(
                "Meters per Second" to 1.0,
                "Kilometers per Hour" to 3.6, // 1 m/s = 3.6 km/h
                "Miles per Hour" to 2.23694, // 1 m/s = 2.23694 mph
                "Feet per Second" to 3.28084, // 1 m/s = 3.28084 ft/s
                "Knots" to 1.94384, // 1 m/s = 1.94384 knots
                "Mach" to 0.002938, // 1 m/s = 0.002938 Mach (at sea level and 20°C)
                "Kilometers per Minute" to 0.06, // 1 m/s = 0.06 km/min
                "Miles per Minute" to 0.037281, // 1 m/s = 0.037281 mi/min
                "Inches per Second" to 39.3701, // 1 m/s = 39.3701 in/s
                "Centimeters per Second" to 100.0 // 1 m/s = 100 cm/s
            ),
            "Temperature" to mapOf(
                "Celsius" to 1.0,
                "Fahrenheit" to 33.8, // Celsius to Fahrenheit: C * 9/5 + 32
                "Kelvin" to 1.0, // Celsius to Kelvin: C + 273.15
                "Rankine" to 1.8, // Celsius to Rankine: (C + 273.15) * 9/5
                "Delisle" to 3.0, // Celsius to Delisle: (100 - C) * 3/2
                "Newton" to 0.333333, // Celsius to Newton: C * 33/100
                "Reaumur" to 0.8, // Celsius to Reaumur: C * 4/5
                "Rømer" to 0.525, // Celsius to Rømer: C * 21/40 + 7.5
                "Lloyd" to 1.0 // Celsius to Lloyd (rarely used): (C + 273.15) * 9/5
            ),
            "Time" to mapOf(
                "Seconds" to 1.0,
                "Minutes" to 1.0 / 60.0, // 1 minute = 60 seconds
                "Hours" to 1.0 / 3600.0, // 1 hour = 3600 seconds
                "Days" to 1.0 / 86400.0, // 1 day = 86400 seconds
                "Weeks" to 1.0 / 604800.0, // 1 week = 604800 seconds
                "Months" to 1.0 / 2628000.0, // Average month = 30.44 days, 1 month = 2628000 seconds
                "Years" to 1.0 / 31536000.0, // 1 year = 365 days = 31536000 seconds
                "Microseconds" to 1.0e6, // 1 second = 1,000,000 microseconds
                "Milliseconds" to 1.0e3, // 1 second = 1,000 milliseconds
                "Nanoseconds" to 1.0e9, // 1 second = 1,000,000,000 nanoseconds
                "Fortnights" to 1.0 / 1209600.0, // 1 fortnight = 1209600 seconds
                "Julian Years" to 1.0 / 31557600.0 // 1 Julian year = 365.25 days = 31557600 seconds
            ),
            "Volume" to mapOf(
                "Liters" to 1.0,
                "Milliliters" to 1000.0, // 1 liter = 1000 milliliters
                "Cubic Meters" to 1e-3, // 1 cubic meter = 1000 liters
                "Cubic Centimeters" to 1000.0, // 1 liter = 1000 cubic centimeters
                "Gallons (US)" to 0.264172, // 1 gallon (US) = 3.78541 liters
                "Gallons (UK)" to 0.219969, // 1 gallon (UK) = 4.54609 liters
                "Quarts (US)" to 1.05669, // 1 quart (US) = 0.946353 liters
                "Pints (US)" to 2.11338, // 1 pint (US) = 0.473176 liters
                "Quarts (UK)" to 0.879876, // 1 quart (UK) = 1.13652 liters
                "Pints (UK)" to 1.13652, // 1 pint (UK) = 0.568261 liters
                "Fluid Ounces (US)" to 33.814, // 1 fluid ounce (US) = 29.5735 milliliters
                "Fluid Ounces (UK)" to 35.1951, // 1 fluid ounce (UK) = 28.4131 milliliters
                "Tablespoons (US)" to 67.628, // 1 tablespoon (US) = 14.7868 milliliters
                "Teaspoons (US)" to 202.884, // 1 teaspoon (US) = 4.92892 milliliters
                "Barrels (US)" to 0.119240, // 1 barrel (US) = 158.987 liters
                "Barrels (UK)" to 0.088046, // 1 barrel (UK) = 159.113 liters
                "Cubic Inches" to 0.0163871 // 1 cubic inch = 16.3871 milliliters
            )
        )

        // Set up category spinner with categories
        val categoryAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, categories)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Handle category selection
        categorySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCategory = categories[position]
                val units = unitsMapping[selectedCategory] ?: arrayOf()

                val unitAdapter = ArrayAdapter(this@MainActivity, android.R.layout.simple_spinner_item, units)
                unitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                fromSpinner.adapter = unitAdapter
                toSpinner.adapter = unitAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        convertButton.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()
            val fromUnit = fromSpinner.selectedItem.toString()
            val toUnit = toSpinner.selectedItem.toString()
            val inputValueString = inputValue.text.toString()

            if (inputValueString.isEmpty()) {
                Toast.makeText(this, "Please enter a value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val inputValueDouble = inputValueString.toDoubleOrNull()

            if (inputValueDouble == null) {
                Toast.makeText(this, "Invalid input value", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val conversionMap = conversionRates[selectedCategory]
            if (conversionMap != null && conversionMap.containsKey(fromUnit) && conversionMap.containsKey(toUnit)) {
                val fromRate = conversionMap[fromUnit] ?: 1.0
                val toRate = conversionMap[toUnit] ?: 1.0
                val result = inputValueDouble * (toRate / fromRate)

                // Format the result and the formula
                val formula = "$inputValueDouble $fromUnit = ${decimalFormat.format(result)} $toUnit"
                val formulaText = "$inputValueDouble $fromUnit = ${decimalFormat.format(result)} $toUnit"


                // Format the result in the "1 week = 604,800 seconds" format
                resultTextView.text = formulaText
//                formulaTextView.text = "Conversion Formula: $formulaText"

            } else {
                Toast.makeText(this, "Conversion not supported", Toast.LENGTH_SHORT).show()
            }
        }




    }


}
