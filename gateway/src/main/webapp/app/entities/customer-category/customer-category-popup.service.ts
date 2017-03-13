import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { CustomerCategory } from './customer-category.model';
import { CustomerCategoryService } from './customer-category.service';
@Injectable()
export class CustomerCategoryPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private customerCategoryService: CustomerCategoryService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.customerCategoryService.find(id).subscribe(customerCategory => {
                this.customerCategoryModalRef(component, customerCategory);
            });
        } else {
            return this.customerCategoryModalRef(component, new CustomerCategory());
        }
    }

    customerCategoryModalRef(component: Component, customerCategory: CustomerCategory): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.customerCategory = customerCategory;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
