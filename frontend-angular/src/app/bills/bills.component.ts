import { Component } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'app-bills',
  templateUrl: './bills.component.html',
  styleUrl: './bills.component.css'
})
export class BillsComponent {

  bills : any;
  customerId! : number;

  constructor(private http:HttpClient, private router : Router, private route : ActivatedRoute) {
    this.customerId=route.snapshot.params['customerId'];
  }

  ngOnInit() {
    this.http.get(`http://localhost:8888/BILLING-SERVICE/customerBill/${this.customerId}`).subscribe({
      next: (data) => {
        this.bills = data;
      },
      error: (err) => {
        console.error("Error fetching bills: ", err);
      }
    })
  }
    getBillDetails(b:any){
      this.router.navigateByUrl("/bill-details/"+b.id)
    }

}
