package com.example.motorgenerator // Replace with your actual package name

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    // Late init declarations for UI components
    private lateinit var editTextManufacturer: EditText
    private lateinit var editTextModel: EditText
    private lateinit var editTextYear: EditText
    private lateinit var editTextMileage: EditText
    private lateinit var buttonSave: Button

    // Companion object for constants (like a static in Java)
    companion object {
        private const val MIN_YEAR = 1900
        private const val MAX_YEAR = 2026
    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize UI components using findViewById
        editTextManufacturer = findViewById(R.id.editTextManufacturer)
        editTextModel = findViewById(R.id.editTextModel)
        editTextYear = findViewById(R.id.editTextYear)
        editTextMileage = findViewById(R.id.editTextMileage)
        buttonSave = findViewById(R.id.buttonSave)

        // Set click listener for Save button using lambda expression
        buttonSave.setOnClickListener {
            validateAndSaveData()
        }
    }

    /**
     * Method to validate all input fields
     * Checks for empty fields and valid year range
     * Returns true if all validations pass, false otherwise
     */
    private fun validateAndSaveData() {
        // Get text from all input fields and trim whitespace using Kotlin's concise syntax
        val manufacturer = editTextManufacturer.text.toString().trim()
        val model = editTextModel.text.toString().trim()
        val yearStr = editTextYear.text.toString().trim()
        val mileageStr = editTextMileage.text.toString().trim()

        // Using a boolean variable to track validation status
        var isValid = true

        // Validate Manufacturer field (not empty)
        if (TextUtils.isEmpty(manufacturer)) {
            editTextManufacturer.error = "Manufacturer is required!"
            isValid = false
        }

        // Validate Model field (not empty)
        if (TextUtils.isEmpty(model)) {
            editTextModel.error = "Model is required!"
            isValid = false
        }

        // Validate Year field with Kotlin's try-catch as expression
        if (TextUtils.isEmpty(yearStr)) {
            editTextYear.error = "Year is required!"
            isValid = false
        } else {
            try {
                val year = yearStr.toInt()
                // Check if year is within valid range
                if (year !in MIN_YEAR..MAX_YEAR) {
                    editTextYear.error = "Year must be between $MIN_YEAR and $MAX_YEAR!"
                    isValid = false
                }
            } catch (_: NumberFormatException) {
                editTextYear.error = "Invalid year format!"
                isValid = false
            }
        }

        // Validate Mileage field
        if (TextUtils.isEmpty(mileageStr)) {
            editTextMileage.error = "Mileage is required!"
            isValid = false
        } else {
            try {
                val mileage = mileageStr.toLong()
                // Check if mileage is non-negative
                if (mileage < 0) {
                    editTextMileage.error = "Mileage cannot be negative!"
                    isValid = false
                }
            } catch (_: NumberFormatException) {
                editTextMileage.error = "Invalid mileage format!"
                isValid = false
            }
        }

        // If all validations pass, show confirmation dialog
        if (isValid) {
            showConfirmationDialog(manufacturer, model, yearStr, mileageStr)
        }
    }

    /**
     * Method to display confirmation dialog with car details
     * @param manufacturer - Car manufacturer name
     * @param model - Car model name
     * @param year - Car year as string
     * @param mileage - Car mileage as string
     */
    private fun showConfirmationDialog(
        manufacturer: String,
        model: String,
        year: String,
        mileage: String
    ) {
        // Use Kotlin's apply scope function for builder configuration
        AlertDialog.Builder(this).apply {
            setTitle("Confirm Car Details")

            // Use Kotlin's string interpolation for message formatting
            setMessage("""
                Manufacturer: $manufacturer
                Model: $model
                Year: $year
                Mileage: $mileage KM
                
                Would you like to save this information?
            """.trimIndent())

            // Set positive button (Save) with lambda
            setPositiveButton("Save") { dialog, _ ->
                // Show success toast message
                Toast.makeText(
                    this@MainActivity,
                    "Car information saved successfully!",
                    Toast.LENGTH_LONG
                ).show()

                // Optional: Here you would typically save to database
                // For now, we just show success message
                dialog.dismiss()
            }

            // Set negative button (Delete/Clear) with lambda
            setNegativeButton("Delete") { dialog, _ ->
                // Clear all input fields
                clearAllFields()

                // Show toast message indicating fields cleared
                Toast.makeText(
                    this@MainActivity,
                    "Fields cleared. You can start over.",
                    Toast.LENGTH_SHORT
                ).show()
                dialog.dismiss()
            }

            // Set neutral button (Cancel) with lambda
            setNeutralButton("Cancel") { dialog, _ ->
                // Just dismiss the dialog without any action
                dialog.dismiss()
            }

            // Create and show the dialog
            create().show()
        }
    }

    /**
     * Method to clear all input fields
     * Resets all EditText fields to empty strings and clears errors
     */
    private fun clearAllFields() {
        // Clear text in all fields using Kotlin's apply for concise code
        editTextManufacturer.apply {
            text.clear()
            error = null
        }
        editTextModel.apply {
            text.clear()
            error = null
        }
        editTextYear.apply {
            text.clear()
            error = null
        }
        editTextMileage.apply {
            text.clear()
            error = null
        }
    }

}