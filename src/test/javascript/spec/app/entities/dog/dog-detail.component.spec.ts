/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { CraftTestModule } from '../../../test.module';
import { DogDetailComponent } from '../../../../../../main/webapp/app/entities/dog/dog-detail.component';
import { DogService } from '../../../../../../main/webapp/app/entities/dog/dog.service';
import { Dog } from '../../../../../../main/webapp/app/entities/dog/dog.model';

describe('Component Tests', () => {

    describe('Dog Management Detail Component', () => {
        let comp: DogDetailComponent;
        let fixture: ComponentFixture<DogDetailComponent>;
        let service: DogService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CraftTestModule],
                declarations: [DogDetailComponent],
                providers: [
                    DogService
                ]
            })
            .overrideTemplate(DogDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DogDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DogService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new Dog(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.dog).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
