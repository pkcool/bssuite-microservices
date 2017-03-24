import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, JhiLanguageService } from 'ng-jhipster';

import { CustomerCategory } from './customer-category.model';
import { CustomerCategoryPopupService } from './customer-category-popup.service';
import { CustomerCategoryService } from './customer-category.service';

@Component({
    selector: 'jhi-customer-category-dialog',
    templateUrl: './customer-category-dialog.component.html'
})
export class CustomerCategoryDialogComponent implements OnInit {

    customerCategory: CustomerCategory;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private jhiLanguageService: JhiLanguageService,
        private alertService: AlertService,
        private customerCategoryService: CustomerCategoryService,
        private eventManager: EventManager
    ) {
        this.jhiLanguageService.setLocations(['customerCategory']);
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.customerCategory.id !== undefined) {
            this.customerCategoryService.update(this.customerCategory)
                .subscribe((res: CustomerCategory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.customerCategoryService.create(this.customerCategory)
                .subscribe((res: CustomerCategory) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: CustomerCategory) {
        this.eventManager.broadcast({ name: 'customerCategoryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-customer-category-popup',
    template: ''
})
export class CustomerCategoryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private customerCategoryPopupService: CustomerCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.customerCategoryPopupService
                    .open(CustomerCategoryDialogComponent, params['id']);
            } else {
                this.modalRef = this.customerCategoryPopupService
                    .open(CustomerCategoryDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
