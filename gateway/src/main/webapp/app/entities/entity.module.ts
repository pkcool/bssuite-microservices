import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BssuiteGatewayCustomerModule } from './customer/customer.module';
import { BssuiteGatewayCustomerCategoryModule } from './customer-category/customer-category.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BssuiteGatewayCustomerModule,
        BssuiteGatewayCustomerCategoryModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BssuiteGatewayEntityModule {}
