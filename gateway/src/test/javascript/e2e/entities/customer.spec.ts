import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('Customer e2e test', () => {

    let navBarPage: NavBarPage;
    let customerDialogPage: CustomerDialogPage;
    let customerComponentsPage: CustomerComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load Customers', () => {
        navBarPage.goToEntity('customer');
        customerComponentsPage = new CustomerComponentsPage();
        expect(customerComponentsPage.getTitle()).toMatch(/bssuiteGatewayApp.customer.home.title/);

    });

    it('should load create Customer dialog', () => {
        customerComponentsPage.clickOnCreateButton();
        customerDialogPage = new CustomerDialogPage();
        expect(customerDialogPage.getModalTitle()).toMatch(/bssuiteGatewayApp.customer.home.createOrEditLabel/);
        customerDialogPage.close();
    });

    it('should create and save Customers', () => {
        customerComponentsPage.clickOnCreateButton();
        customerDialogPage.setCodeInput('code');
        expect(customerDialogPage.getCodeInput()).toMatch('code');
        customerDialogPage.setNameInput('name');
        expect(customerDialogPage.getNameInput()).toMatch('name');
        customerDialogPage.setTradingNameInput('tradingName');
        expect(customerDialogPage.getTradingNameInput()).toMatch('tradingName');
        customerDialogPage.setAbnInput('abn');
        expect(customerDialogPage.getAbnInput()).toMatch('abn');
        customerDialogPage.save();
        expect(customerDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CustomerComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-customer div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CustomerDialogPage {
    modalTitle = element(by.css('h4#myCustomerLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    nameInput = element(by.css('input#field_name'));
    tradingNameInput = element(by.css('input#field_tradingName'));
    abnInput = element(by.css('input#field_abn'));

    getModalTitle() {
        return this.modalTitle.getAttribute('jhiTranslate');
    }

    setCodeInput = function (code) {
        this.codeInput.sendKeys(code);
    }

    getCodeInput = function () {
        return this.codeInput.getAttribute('value');
    }

    setNameInput = function (name) {
        this.nameInput.sendKeys(name);
    }

    getNameInput = function () {
        return this.nameInput.getAttribute('value');
    }

    setTradingNameInput = function (tradingName) {
        this.tradingNameInput.sendKeys(tradingName);
    }

    getTradingNameInput = function () {
        return this.tradingNameInput.getAttribute('value');
    }

    setAbnInput = function (abn) {
        this.abnInput.sendKeys(abn);
    }

    getAbnInput = function () {
        return this.abnInput.getAttribute('value');
    }

    save() {
        this.saveButton.click();
    }

    close() {
        this.closeButton.click();
    }

    getSaveButton() {
        return this.saveButton;
    }
}
