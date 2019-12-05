
describe('Test opening up the main webpage', function (){
    beforeEach('Visit page', function () {
        visit_home_page();
        register_as_user();
    });

    it('Test login with user', function () {
        cy.location('pathname').should('include', 'login');
        login_as_user();
    });

    it('Test the About page', function () {
        cy.visit('localhost:8080');
        cy.get('a').contains('About').click();
        cy.location('pathname').should('include', 'about');
    });
});



function visit_home_page() {
    cy.visit('http://localhost:8080');
}

function register_as_user() {
    cy.get('button').contains('Register').click();
    cy.location('pathname').should('include', 'register');
    cy.get('#name').type('cypress');
    cy.get('#surname').type('test');
    cy.get('#username').type('cypressuser');
    cy.get('#email').type('cypressemail@gmail.com');
    cy.get('#password').type('cypress');
    cy.get('button').contains('Register User').click();
}


function login_as_user() {
    cy.get('#emailm').type('cypressuser');
    cy.get('#password').type('cypress');
    cy.contains('Login').click()
}

function I_see_navbar() {
    cy.url().should('contains', 'http://localhost:8080/artists/');
    cy.get('h1').should('contain', 'elton john')

}