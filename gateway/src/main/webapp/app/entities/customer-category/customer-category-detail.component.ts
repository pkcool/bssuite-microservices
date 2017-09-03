import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CustomerCategory } from './customer-category.model';
import { CustomerCategoryService } from './customer-category.service';

@Component({
    selector: 'jhi-customer-category-detail',
    templateUrl: './customer-category-detail.component.html'
})
export class CustomerCategoryDetailComponent implements OnInit, OnDestroy {

    customerCategory: CustomerCategory;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private customerCategoryService: CustomerCategoryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCustomerCategories();
    }

    load(id) {
        this.customerCategoryService.find(id).subscribe((customerCategory) => {
            this.customerCategory = customerCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCustomerCategories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'customerCategoryListModification',
            (response) => this.load(this.customerCategory.id)
        );
    }
}
