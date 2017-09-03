/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BssuiteGatewayTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CustomerCategoryDetailComponent } from '../../../../../../main/webapp/app/entities/customer-category/customer-category-detail.component';
import { CustomerCategoryService } from '../../../../../../main/webapp/app/entities/customer-category/customer-category.service';
import { CustomerCategory } from '../../../../../../main/webapp/app/entities/customer-category/customer-category.model';

describe('Component Tests', () => {

    describe('CustomerCategory Management Detail Component', () => {
        let comp: CustomerCategoryDetailComponent;
        let fixture: ComponentFixture<CustomerCategoryDetailComponent>;
        let service: CustomerCategoryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BssuiteGatewayTestModule],
                declarations: [CustomerCategoryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CustomerCategoryService,
                    JhiEventManager
                ]
            }).overrideTemplate(CustomerCategoryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CustomerCategoryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CustomerCategoryService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CustomerCategory(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.customerCategory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
