package com.polstat.perpustakaan.mapper;

import com.polstat.perpustakaan.dto.LoanDto;
import com.polstat.perpustakaan.entity.Loan;
import com.polstat.perpustakaan.entity.Book;
import com.polstat.perpustakaan.entity.Member;

public class LoanMapper {

    public static Loan mapToLoan(LoanDto loanDto, Member member, Book book) {
        return Loan.builder()
                .id(loanDto.getId())
                .member(member)
                .book(book)
                .borrowDate(loanDto.getBorrowDate())
                .returnDate(loanDto.getReturnDate())
                .loanStatus(Loan.LoanStatus.valueOf(loanDto.getLoanStatus().toUpperCase())) // Convert String to Enum
                .overdueDays(loanDto.getOverdueDays())
                .build();
    }

    public static LoanDto mapToLoanDto(Loan loan) {
        return LoanDto.builder()
                .id(loan.getId())
                .memberId(loan.getMember().getId())
                .bookId(loan.getBook().getId())
                .borrowDate(loan.getBorrowDate())
                .returnDate(loan.getReturnDate())
                .loanStatus(loan.getLoanStatus().name()) // Convert Enum to String
                .overdueDays(loan.getOverdueDays())
                .build();
    }
}
