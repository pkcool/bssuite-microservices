import { browser, element, by, $ } from 'protractor';
import { NavBarPage } from './../page-objects/jhi-page-objects';
const path = require('path');

describe('CustomerCategory e2e test', () => {

    let navBarPage: NavBarPage;
    let customerCategoryDialogPage: CustomerCategoryDialogPage;
    let customerCategoryComponentsPage: CustomerCategoryComponentsPage;
    const fileToUpload = '../../../../main/webapp/content/images/logo-jhipster.png';
    const absolutePath = path.resolve(__dirname, fileToUpload);
    

    beforeAll(() => {
        browser.get('/');
        browser.waitForAngular();
        navBarPage = new NavBarPage();
        navBarPage.getSignInPage().autoSignInUsing('admin', 'admin');
        browser.waitForAngular();
    });

    it('should load CustomerCategories', () => {
        navBarPage.goToEntity('customer-category');
        customerCategoryComponentsPage = new CustomerCategoryComponentsPage();
        expect(customerCategoryComponentsPage.getTitle()).toMatch(/bssuiteGatewayApp.customerCategory.home.title/);

    });

    it('should load create CustomerCategory dialog', () => {
        customerCategoryComponentsPage.clickOnCreateButton();
        customerCategoryDialogPage = new CustomerCategoryDialogPage();
        expect(customerCategoryDialogPage.getModalTitle()).toMatch(/bssuiteGatewayApp.customerCategory.home.createOrEditLabel/);
        customerCategoryDialogPage.close();
    });

    it('should create and save CustomerCategories', () => {
        customerCategoryComponentsPage.clickOnCreateButton();
        customerCategoryDialogPage.setCodeInput('code');
        expect(customerCategoryDialogPage.getCodeInput()).toMatch('code');
        customerCategoryDialogPage.setNameInput('name');
        expect(customerCategoryDialogPage.getNameInput()).toMatch('name');
        customerCategoryDialogPage.setTradingNameInput('tradingName');
        expect(customerCategoryDialogPage.getTradingNameInput()).toMatch('tradingName');
        customerCategoryDialogPage.save();
        expect(customerCategoryDialogPage.getSaveButton().isPresent()).toBeFalsy();
    }); 

    afterAll(() => {
        navBarPage.autoSignOut();
    });
});

export class CustomerCategoryComponentsPage {
    createButton = element(by.css('.jh-create-entity'));
    title = element.all(by.css('jhi-customer-category div h2 span')).first();

    clickOnCreateButton() {
        return this.createButton.click();
    }

    getTitle() {
        return this.title.getAttribute('jhiTranslate');
    }
}

export class CustomerCategoryDialogPage {
    modalTitle = element(by.css('h4#myCustomerCategoryLabel'));
    saveButton = element(by.css('.modal-footer .btn.btn-primary'));
    closeButton = element(by.css('button.close'));
    codeInput = element(by.css('input#field_code'));
    nameInput = element(by.css('input#field_name'));
    tradingNameInput = element(by.css('input#field_tradingName'));

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
