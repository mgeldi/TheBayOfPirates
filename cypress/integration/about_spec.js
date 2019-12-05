
describe('Basic tests for the webpage', function (){

    beforeEach('Visit page', function () {
        Cypress.Cookies.preserveOnce('sessionid', 'remember_token');
        visit_home_page();
    });

    it('Test with wrong user registration', function () {
       register_wrong_user();
    });

    it('Test login with user', function () {
        register_as_user();
        login_as_user();
    });

    it('Test the Home page', function () {
       I_see_navbar();
       cy.get('h1').should('contain', 'Hello cypressemail@gmail.com!');
       cy.get('h1').should('contain', 'Welcome to The Bay Of Pirates!');
    });

    it('Test the About page', function () {
        I_see_navbar();
        cy.get('.navbar').contains('About Us').click();
        cy.url().should('contain', 'about');
        cy.get('h1').should('contain', 'About us');
    });
});


function visit_home_page() {
    cy.visit('http://localhost:8080');
}

function register_as_user() {
    I_see_navbar();
    cy.get('button').contains('Register').click();
    cy.url().should('contain', 'register');
    cy.get('#name').type('cypress');
    cy.get('#surname').type('test');
    cy.get('#username').type('cypressuser');
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('button').contains('Register User').click();
}

function register_wrong_user() {
    cy.get('button').contains('Register').click();
    cy.url().should('contain', 'register');
    cy.get('#name').type('c');
    cy.get('#surname').type('t');
    cy.get('#username').type('c');
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('button').contains('Register User').click();
    cy.url().should('contain', 'register');
}


function login_as_user() {
    cy.visit('http://localhost:8080/login');
    I_see_login_page();
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('button').contains('Login').click();
}

function I_see_navbar() {
    cy.get('.navbar').should("contain", 'Home');
    cy.get('.navbar').should("contain", 'About Us');
}

function I_see_login_page() {
    cy.url().should('contain', 'login');
    I_see_navbar();
    cy.get('#email').should('have.id', 'email');
    cy.get('#password').should('have.id', 'password');
    cy.get('button').should('contain', 'Login');
    cy.get('button').should('contain', 'Register');
}