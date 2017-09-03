import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { CustomerCategory } from './customer-category.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CustomerCategoryService {

    private resourceUrl = 'customerservice/api/customer-categories';

    constructor(private http: Http) { }

    create(customerCategory: CustomerCategory): Observable<CustomerCategory> {
        const copy = this.convert(customerCategory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(customerCategory: CustomerCategory): Observable<CustomerCategory> {
        const copy = this.convert(customerCategory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<CustomerCategory> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(customerCategory: CustomerCategory): CustomerCategory {
        const copy: CustomerCategory = Object.assign({}, customerCategory);
        return copy;
    }
}
