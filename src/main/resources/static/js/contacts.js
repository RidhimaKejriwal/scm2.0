console.log("contacts.js");

const baseURL = "http://localhost:8081";

const viewContactModal = document.getElementById("view_contact_modal");

// options with default values
const options = {
    placement: 'bottom-right',
    backdrop: 'dynamic',
    backdropClasses:
        'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
    closable: true,
    onHide: () => {
        console.log('modal is hidden');
    },
    onShow: () => {
        console.log('modal is shown');
    },
    onToggle: () => {
        console.log('modal has been toggled');
    },
};

// instance options object
const instanceOptions = {
    id: 'view_contact_modal',
    override: true
};

const contactModal = new Modal(viewContactModal, options, instanceOptions);

function openContactModal() {
    contactModal.show();
}

function closeContactModal() {
    contactModal.hide();
}

async function loadContactdata(id) {
    try {
        console.log(id);
        const data = await (await fetch(`${baseURL}/api/contacts/${id}`)).json();
        console.log(data);
        document.querySelector("#contact_picture").src = data.picture;
        document.querySelector("#contact_name").innerHTML = data.name;
        document.querySelector("#contact_email").innerHTML = data.email;
        document.querySelector("#contact_phoneNumber").innerHTML = data.phoneNumber;
        document.querySelector("#contact_address").innerHTML = data.address;
        document.querySelector("#contact_description").innerHTML = data.description;
        // document.querySelector("#contact_favorite").setAttribute("data-th-if", data.favorite);
        document.querySelector("#contact_websiteLink").href = data.websiteLink;
        document.querySelector("#contact_websiteLink").innerHTML = data.websiteLink;
        document.querySelector("#contact_linkedInLink").href = data.linkedInLink;
        document.querySelector("#contact_linkedInLink").innerHTML = data.linkedInLink;

        const contactFavorite = document.querySelector("#contact_favorite");
        if(data.favorite) {
            contactFavorite.innerHTML = "<i class='fa-solid fa-heart text-pink-600'></i>";
        }

        openContactModal();
    } catch (error) {
        console.log(error);
    }
}


// delete contact
async function deleteContact(id) {
    
    Swal.fire({
        title: "Do you want to delete the contact?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
      }).then((result) => {
        /* Read more about isConfirmed, isDenied below */
        if (result.isConfirmed) {
          const url = `${baseURL}/user/contacts/delete/` + id;
          window.location.replace(url);
        //   Swal.fire("Deleted!", "", "success");
        }
      });

}