describe('Test opening up the main webpage', function (){
    it('Test the About page', function () {
        cy.visit('localhost:8080');
        cy.get('a').contains('About').click();
        cy.location('pathname').should('include', 'about');
    })
});