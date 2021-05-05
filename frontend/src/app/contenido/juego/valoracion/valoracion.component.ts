import { Component, OnInit } from '@angular/core';
import * as $ from "jquery";

@Component({
  selector: 'app-valoracion',
  templateUrl: './valoracion.component.html',
  styleUrls: ['./valoracion.component.css']
})
export class ValoracionComponent implements OnInit {

  constructor() { }

  ngOnInit(): void {

    $(document).ready(function(){
  
      /* 1. Visualizing things on Hover - See next part for action on click */
      $('#stars li').on('mouseover', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently mouse on
       
        // Now highlight all the stars that's not after the current hovered star
        $(this).parent().children('li.star').each(function(e){
          if (e < onStar) {
            $(this).addClass('hover');
          }
          else {
            $(this).removeClass('hover');
          }
        });
        
      }).on('mouseout', function(){
        $(this).parent().children('li.star').each(function(e){
          $(this).removeClass('hover');
        });
      });
      
      
      /* 2. Action to perform on click */
      $('#stars li').on('click', function(){
        var onStar = parseInt($(this).data('value'), 10); // The star currently selected
        var stars = $(this).parent().children('li.star');
        
        for (let i = 0; i < stars.length; i++) {
          $(stars[i]).removeClass('selected');
        }
        
        for (let i = 0; i < onStar; i++) {
          $(stars[i]).addClass('selected');
        }
        
      });      
    });

  }

}
