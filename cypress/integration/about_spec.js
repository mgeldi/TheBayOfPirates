describe('Basic tests for the webpage', function () {

    before('Setup Cookies', function () {
        Cypress.Cookies.preserveOnce('session_id', 'remember_token');
    })

    beforeEach('Visit page', function () {
        visit_home_page();
    });

    it('Test with wrong user registration', function () {
        register_wrong_user();
        cy.get('li').should("contain",'Name must be between 3 and 24 characters long!');
    });

    it('Test login with user', function () {
        register_as_user();
        login_as_user();
    });

    it('Test the Home page', function () {
        login_as_user();
        I_see_navbar();
        cy.get('h1').should('contain', 'Hello cypressemail@gmail.com!');
        cy.get('h1').should('contain', 'Welcome to The Bay Of Pirates!');
    });

    it('Test the About page', function () {
        login_as_user();
        I_see_navbar();
        cy.get('.navbar').contains('About Us').click();
        cy.url().should('contain', '/about');
        cy.get('h1').should('contain', 'About us');
    });

    it('Test About page without login', function () {
        I_see_navbar();
        cy.get('.navbar').contains('About Us').click();
        cy.url().should('contain', '/about');
        cy.get('h1').should('contain', 'About us');
    });

    it('Test sign in link ', function () {
        cy.get('button').contains('Register').click();
        cy.get('p').should('contain', 'Already have an account? Sign in').click();
        I_see_login_page();

    });
});


function visit_home_page() {
    cy.visit('http://localhost:8080/');
}

function register_as_user() {
    I_see_navbar();
    cy.get('button').contains('Register').click();
    cy.url().should('contain', '/register');
    cy.get('#name').type('cypress');
    cy.get('#surname').type('test');
    cy.get('#username').type('cypressuser');
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('button').contains('Register User').click();
}

function register_wrong_user() {
    cy.get('button').contains('Register').click();
    cy.url().should('contain', '/register');
    cy.get('#name').type('c');
    cy.get('#surname').type('tttttt');
    cy.get('#username').type('cdsdsw');
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('button').contains('Register User').click();
    cy.url().should('contain', '/register');
}


function login_as_user() {
    visit_home_page();
    I_see_login_page();
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('button').contains('Login').click();
}

function I_see_navbar() {
    cy.get('.navbar').should("contain", 'Home');
    cy.get('.navbar').should("contain", 'About Us');
}

function I_see_allert() {
    register_wrong_user();
    cy.get('alert alert-success alert-dismissible col-sm-12').should("contain",'Name must be between 3 and 24 characters long!');
}

function I_see_login_page() {
    cy.url().should('contain', '/login');
    I_see_navbar();
    cy.get('#email').should('have.id', 'email');
    cy.get('#password').should('have.id', 'password');
    cy.get('button').should('contain', 'Login');
    cy.get('button').should('contain', 'Register');
}