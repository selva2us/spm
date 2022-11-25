package com.ms.spm.controller;

import com.ms.spm.dto.ResponseMessage;
import com.ms.spm.entity.Goods;
import com.ms.spm.helper.CSVHelper;
import com.ms.spm.payload.GoodsDto;
import com.ms.spm.services.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@CrossOrigin("http://localhost:8081")
@Controller
@RequestMapping("/api/goods")
public class CSVController {

    @Autowired
    CSVService fileService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.save(file);

                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!";
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/")
    public ResponseEntity<List<Goods>> getAllGoods() {
        try {
            List<Goods> goods = fileService.getAllGoods();
            if (goods.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(goods, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((List<Goods>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @PostMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createGood(@RequestBody GoodsDto goods){
        try{
            Goods ds = fileService.saveGood(goods);
            return new ResponseEntity<>(ds, HttpStatus.OK);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage("Error in good creation"));
        }
    }

    @PutMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Goods> updateGood(@PathVariable(value = "id") Long id, @RequestBody GoodsDto goods){
        try {
        Goods ds = fileService.updateGoods(id, goods);
        return new ResponseEntity<>(ds,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>((Goods) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<GoodsDto>> updateGoods(@RequestBody List<GoodsDto> goods){
        try {
            fileService.updatedAllGoods(goods);
            return new ResponseEntity<List<GoodsDto>>(goods,HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<List<GoodsDto>>((List<GoodsDto>) null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @RequestMapping(value = "/{id}",produces = "application/json",method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteGood(@PathVariable(value="id") Long id){
       try{
           fileService.deleteGood(id);
           return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Deleted"));
       }catch (Exception e )
       {
           return new ResponseEntity<Object>( null, HttpStatus.INTERNAL_SERVER_ERROR);
       }
    }

    @RequestMapping(value = "/",produces = "application/json", method=RequestMethod.DELETE)
    public ResponseEntity<Object> deleteGoods(@RequestBody List<GoodsDto> goods){
        try{
            fileService.deleteGoods(goods);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage("Deleted"));
        }catch (Exception e )
        {
            return new ResponseEntity<Object>( null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
