import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Customer } from './customer.model';
import { CustomerPopupService } from './customer-popup.service';
import { CustomerService } from './customer.service';

@Component({
    selector: 'jhi-customer-dialog',
    templateUrl: './customer-dialog.component.html'
})
export class CustomerDialogComponent implements OnInit {

    customer: Customer;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private customerService: CustomerService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.customer.id !== undefined) {
            this.subscribeToSaveResponse(
                this.customerService.update(this.customer));
        } else {
            this.subscribeToSaveResponse(
                this.customerService.create(this.customer));
        }
    }

    private subscribeToSaveResponse(result: Observable<Customer>) {
        result.subscribe((res: Customer) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Customer) {
        this.eventManager.broadcast({ name: 'customerListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-customer-popup',
    template: ''
})
export class CustomerPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerPopupService: CustomerPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.customerPopupService
                    .open(CustomerDialogComponent as Component, params['id']);
            } else {
                this.customerPopupService
                    .open(CustomerDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
