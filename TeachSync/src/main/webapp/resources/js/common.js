function showById(id) {
	$("#" + id).removeClass("visually-hidden");
}

function hideById(id) {
    $("#" + id).addClass("visually-hidden");
}

function enableAllInputIn(id, type) {
    if (type == null) {
        $("#"+id+" input").prop("disabled", false);
    } else {
        $("#"+id+" input[type="+type+"]").prop("disabled", false);
    }
}

function disableAllInputIn(id, type) {
    if (type == null) {
        $("#"+id+" input").prop("disabled", true);
    } else {
        $("#"+id+" input[type="+type+"]").prop("disabled", true);
    }
}

function copyToClipboard(id) {
    const fadeTime = 1500;

    navigator.clipboard.writeText($("#" + id).text());

    $("body").prepend(
        '<div class="fixed-top d-flex justify-content-center" id="alert">' +
        '   <p class="ts-bg-grey-subtle rounded-pill py-2 px-5" style="width: fit-content;">' +
        '       Copied to clipboard' +
        '   </p>' +
        '</div>');

    $("#alert").fadeOut(fadeTime);

    setTimeout(function() { $("#alert").remove(); }, fadeTime);
}

/* TODO: chưa import js cho datatable */
$(document).ready( function () {
    $('#myTable').DataTable();
} );