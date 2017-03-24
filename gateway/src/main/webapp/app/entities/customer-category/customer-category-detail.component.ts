import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { CustomerCategory } from './customer-category.model';
import { CustomerCategoryService } from './customer-category.service';

@Component({
    selector: 'jhi-customer-category-detail',
    templateUrl: './customer-category-detail.component.html'
})
export class CustomerCategoryDetailComponent implements OnInit, OnDestroy {

    customerCategory: CustomerCategory;
    private subscription: any;

    constructor(
        private jhiLanguageService: JhiLanguageService,
        private customerCategoryService: CustomerCategoryService,
        private route: ActivatedRoute
    ) {
        this.jhiLanguageService.setLocations(['customerCategory']);
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe(params => {
            this.load(params['id']);
        });
    }

    load (id) {
        this.customerCategoryService.find(id).subscribe(customerCategory => {
            this.customerCategory = customerCategory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
    }

}
