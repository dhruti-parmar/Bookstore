// JavaScript for the bookstore application

// Import Bootstrap (assuming it's a module)
// import * as bootstrap from "bootstrap"

document.addEventListener("DOMContentLoaded", () => {
  // Auto-hide alerts after 5 seconds
  setTimeout(() => {
    var alerts = document.querySelectorAll(".alert:not(.alert-permanent)")
    alerts.forEach((alert) => {
      var bsAlert = new bootstrap.Alert(alert)
      bsAlert.close()
    })
  }, 5000)

  // Form validation
  var forms = document.querySelectorAll(".needs-validation")
  Array.prototype.slice.call(forms).forEach((form) => {
    form.addEventListener(
      "submit",
      (event) => {
        if (!form.checkValidity()) {
          event.preventDefault()
          event.stopPropagation()
        }
        form.classList.add("was-validated")
      },
      false,
    )
  })
})