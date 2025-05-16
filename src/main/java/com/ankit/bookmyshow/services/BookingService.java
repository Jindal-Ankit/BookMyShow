package com.ankit.bookmyshow.services;


import com.ankit.bookmyshow.exceptions.SeatNoLongerAvailableException;
import com.ankit.bookmyshow.exceptions.ShowDoesNotExists;
import com.ankit.bookmyshow.exceptions.UserDoesNotExists;
import com.ankit.bookmyshow.models.Booking;
import com.ankit.bookmyshow.models.Show;
import com.ankit.bookmyshow.models.ShowSeat;
import com.ankit.bookmyshow.models.User;
import com.ankit.bookmyshow.models.enums.BookingStatus;
import com.ankit.bookmyshow.models.enums.ShowSeatStatus;
import com.ankit.bookmyshow.repositories.BookingRepository;
import com.ankit.bookmyshow.repositories.ShowRepository;
import com.ankit.bookmyshow.repositories.ShowSeatRepository;
import com.ankit.bookmyshow.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.locks.ReentrantLock;

@Service
public class BookingService {
    UserRepository userRepository;
    ShowSeatRepository showSeatRepository;
    ShowRepository showRepository;
    BookingRepository bookingRepository;
    PaymentService paymentService;

    public BookingService(BookingRepository bookingRepository,
                          UserRepository userRepository,
                          ShowSeatRepository showSeatRepository,
                          ShowRepository showRepository,
                          PaymentService paymentService) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.showSeatRepository = showSeatRepository;
        this.showRepository = showRepository;
        this.paymentService = paymentService;
    }

    @Transactional
    public Booking bookTicket(Long userId, List<Long> showSeatIds, Long showId){


        // check if user is present
        Optional<User> optionalUser = userRepository.findById(userId);
        if(optionalUser.isEmpty()){
            throw new UserDoesNotExists("No user with id:" + userId + " found.");
        }
        // check if seats are available
        List<ShowSeat> showSeats =  showSeatRepository.findShowSeatsByIdsAndLock(showSeatIds);
        if (showSeatIds.size() != showSeats.size()){
            throw new RuntimeException("All Seats are not available.");
        }
        // check if show is present
        Optional<Show> optionalShow = showRepository.findById(showId);
        if(optionalShow.isEmpty()){
            throw new ShowDoesNotExists("Show "+ showId + " does not exists.");
        }

        // Book seats if all above validation success
        for (ShowSeat showSeat : showSeats){
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new SeatNoLongerAvailableException("Some of selected seats are not available.");
            }
        }

        User user = optionalUser.get();
        Show show = optionalShow.get();

        Booking booking = new Booking();
        booking.setBookingStatus(BookingStatus.PENDING);
        booking.setRefNum("Booking_" + userId + "_" + showId + "_"+showSeatIds.toString());
        booking.setUser(user);
        booking.setShowSeats(showSeats);
        booking.setAmount(1000.0);

        /*
        to perform booking we need to block the seats i.e making their status pending, so that other requests can't access these
        */
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for(ShowSeat showSeat : showSeats){
            if (!showSeat.getShowSeatStatus().equals(ShowSeatStatus.AVAILABLE)){
                throw new SeatNoLongerAvailableException("Some of selected seats are not available.");
            }
        }
        for(ShowSeat showSeat : showSeats){
            showSeat.setShowSeatStatus(ShowSeatStatus.BLOCKED);
        }
        showSeatRepository.saveAll(showSeats);

        // call third party payment api
        double amount = booking.getAmount();
        boolean paymentStatus = paymentService.makePayment(userId, amount);
        // if payment successfull then change the status of selected showseats to BOOKED and save to DB
        // and change booking status to CONFIRMED
        if (paymentStatus){
            showSeats.forEach(showSeat -> showSeat.setShowSeatStatus(ShowSeatStatus.BOOKED));
            showSeatRepository.saveAll(showSeats);
            booking.setBookingStatus(BookingStatus.CONFIRMED);
            bookingRepository.save(booking);
        }




        return booking;

    }
}
