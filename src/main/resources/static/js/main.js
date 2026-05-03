/**
 * University Management System - Custom UI Interactions
 * handles: Alert dismissal, Form confirmation, and Sidebar states.
 */

document.addEventListener('DOMContentLoaded', function() {

    // 1. Auto-hide Alert Messages
    // This looks for Bootstrap alerts and hides them after 5 seconds
    const alerts = document.querySelectorAll('.alert-dismissible');
    alerts.forEach(function(alert) {
        setTimeout(function() {
            const bsAlert = new bootstrap.Alert(alert);
            bsAlert.close();
        }, 5000);
    });

    // 2. Delete Confirmation Tooltip
    // Adds a second layer of safety for delete buttons
    const deleteButtons = document.querySelectorAll('.btn-del');
    deleteButtons.forEach(button => {
        button.addEventListener('click', function(e) {
            const message = this.getAttribute('data-confirm-message') || 'Are you sure you want to delete this record?';
            if (!confirm(message)) {
                e.preventDefault();
            }
        });
    });

    // 3. Form Validation Highlight
    // Simple helper to highlight fields that have errors
    const errorFields = document.querySelectorAll('.text-danger');
    errorFields.forEach(error => {
        const input = error.previousElementSibling;
        if (input && (input.tagName === 'INPUT' || input.tagName === 'SELECT')) {
            input.classList.add('is-invalid');
        }
    });

    // 4. Log startup to Console (helpful for debugging)
    console.log("✅ UniManage UI Script Loaded Successfully.");
});