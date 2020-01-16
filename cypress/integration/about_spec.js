describe('Basic tests for the webpage', function () {

    before('Setup Cookies', function () {
        Cypress.Cookies.preserveOnce('session_id', 'remember_token');
    })

    beforeEach('Visit page', function () {
        visit_home_page();
    });

    it('Test to register', function () {
        register_as_user();
    });

    it('Test to register invalid user', function () {
        register_wrong_user();
    });

    it('Login as valid user', function () {
        login_as_user();
    })

    it('Visit About page', function () {
        visit_about_page();
    });

    it('Visit Torrent page and upload a torrent', function () {
        login_as_user();
        visit_torrent_upload_page();
        I_see_torrent_upload_page();
        upload_a_torrent();
        I_see_uploaded_torrent_page();
    });

    it('Upload same torrent again', function () {
        login_as_user();
        visit_torrent_upload_page();
        I_see_torrent_upload_page();
        upload_a_torrent();
        I_see_torrent_upload_page();
        torrent_already_exists();
    });

    it('Delete the previously uploaded test torrent', function () {
        login_as_user();
        visit_uploaded_torrent_page();
        delete_torrent();
    });

    it('Visit profile page', function () {
        login_as_user();
        visit_profile_page();
    })
});


function visit_home_page() {
    cy.visit('http://localhost:8080/');
}

function register_as_user() {
    I_see_navbar_as_non_user();
    cy.get('button').contains('Login').click();
    cy.get('a').contains('Register').click();
    cy.get('#name').type('cypress');
    cy.get('#surname').type('test');
    cy.get('#username').type('cypressuser');
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('#passwordRepeat').type('cypress');
    cy.get('button').contains('Sign up').click();
}

function register_wrong_user() {
    I_see_navbar_as_non_user();
    cy.get('button').contains('Login').click();
    cy.get('a').contains('Register').click();
    cy.get('#name').type('cypress');
    cy.get('#surname').type('test');
    cy.get('#username').type('c');
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('#passwordRepeat').type('cypress');
    cy.get('button').contains('Sign up').click();
    cy.url().should('contain', 'correct');
}

function login_as_user() {
    I_see_navbar_as_non_user();
    cy.get('button').contains('Login').click();
    cy.get('#emailLogin').type('cypressemail@gmail.com');
    cy.get('#passwordLogin').type('cypress');
    cy.get('button').contains('Sign in').click();
    cy.get('h1').should('contain', 'Welcome to the Bay of Pirates, cypressuser');
    I_see_navbar_as_user();
}

function I_see_navbar_as_non_user() {
    cy.get('.navbar').should('contain', 'Home');
    cy.get('.navbar').should('contain', 'About');
    cy.get('.navbar').should('contain', 'Login');
}

function I_see_navbar_as_user() {
    cy.get('.navbar').should('contain', 'Home');
    cy.get('.navbar').should('contain', 'About');
    cy.get('.navbar').should('contain', 'Logout');
    cy.get('.navbar').should('contain', 'Profile');
    cy.get('.navbar').should('contain', 'Torrents');
    cy.get('.navbar').should('contain', 'Search');
}

function I_see_about() {
    I_see_navbar_as_non_user();
    cy.url().should('contain', '/about');
    cy.get('h1').should('contain', 'About us');
}

function visit_about_page() {
    I_see_navbar_as_non_user();
    cy.get('a').contains('About').click();
    I_see_about();
}

function upload_a_torrent() {
    const fileName = 'arch.torrent';
    const testFile = new File(['content'], 'arch.torrent');
    const dataTransfer = new DataTransfer();

    cy.get("input[type='file']").then(subject => {
        dataTransfer.items.add(testFile);
        const el = subject[0];

        el.files = dataTransfer.files;
        cy.log(el.files[0] instanceof File);
    });

    cy.get('textarea').type("This is some description for cypress testing. Blablablalblablablalblabla");

    cy.get('button').contains('Upload').click();
}

function I_see_torrent_upload_page() {
    cy.url().should("contain", "/torrent/upload");
    cy.get('button').should("contain", "Upload");
    cy.get('textarea').should("exist");
    cy.get('input').should("exist");
}

function I_see_uploaded_torrent_page() {
    cy.url().should("contain", "/torrent/id=")
    cy.get('div').should("contain", "arch");
    cy.get('div').should("contain", "cypressemail@gmail.com");
    cy.get('div').should("contain", "This is some description for cypress testing. Blablablalblablablalblabla");
    cy.get('button').should("contain", "Delete");
}

function visit_torrent_upload_page() {
    I_see_navbar_as_user();
    cy.get('a').contains("Torrent").click();
    I_see_torrent_upload_page();
}

function visit_uploaded_torrent_page() {
    cy.visit("http://localhost:8080/torrent/name=arch");
}

function delete_torrent() {
    cy.get('button').contains("Delete").click();
    I_see_torrent_upload_page();
}

function torrent_already_exists() {
    cy.get('div').should("contain", "Torrent with that name already exists!");
}

function visit_profile_page() {
    cy.get('a').contains('Profile').click();
    I_see_profile_page();
}

function I_see_profile_page() {
    I_see_navbar_as_user();
    cy.url().should("contain", "/user/profile=cypressuser");
}