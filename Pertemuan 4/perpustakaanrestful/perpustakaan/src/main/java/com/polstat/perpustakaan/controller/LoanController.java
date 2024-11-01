package com.polstat.perpustakaan.controller;

import com.polstat.perpustakaan.dto.LoanDto;
import com.polstat.perpustakaan.service.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private LoanService loanService;

    @PostMapping("/loan")
    public ResponseEntity<String> loanBook(@RequestBody LoanDto loanDto) {
        loanService.loanBook(loanDto);
        return ResponseEntity.ok("Buku sukses terpinjam!");
    }

    @PostMapping("/return/{id}")
    public ResponseEntity<String> returnBook(@PathVariable Long id) {
        loanService.returnBook(id);
        return ResponseEntity.ok("Buku sukses dikembalikan!");
    }

    @GetMapping("/getall")
    public ResponseEntity<List<LoanDto>> getAllLoans() {
        return ResponseEntity.ok(loanService.getAllLoans());
    }
}
