import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerCategory } from './customer-category.model';
import { CustomerCategoryPopupService } from './customer-category-popup.service';
import { CustomerCategoryService } from './customer-category.service';

@Component({
    selector: 'jhi-customer-category-delete-dialog',
    templateUrl: './customer-category-delete-dialog.component.html'
})
export class CustomerCategoryDeleteDialogComponent {

    customerCategory: CustomerCategory;

    constructor(
        private customerCategoryService: CustomerCategoryService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.customerCategoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'customerCategoryListModification',
                content: 'Deleted an customerCategory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-customer-category-delete-popup',
    template: ''
})
export class CustomerCategoryDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private customerCategoryPopupService: CustomerCategoryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.customerCategoryPopupService
                .open(CustomerCategoryDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
