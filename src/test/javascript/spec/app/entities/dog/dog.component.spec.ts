/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { CraftTestModule } from '../../../test.module';
import { DogComponent } from '../../../../../../main/webapp/app/entities/dog/dog.component';
import { DogService } from '../../../../../../main/webapp/app/entities/dog/dog.service';
import { Dog } from '../../../../../../main/webapp/app/entities/dog/dog.model';

describe('Component Tests', () => {

    describe('Dog Management Component', () => {
        let comp: DogComponent;
        let fixture: ComponentFixture<DogComponent>;
        let service: DogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CraftTestModule],
                declarations: [DogComponent],
                providers: [
                    DogService
                ]
            })
            .overrideTemplate(DogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new Dog(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.dogs[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
